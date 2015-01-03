import akka.actor.ActorRef;
import akka.actor.Props;
import controllers.actors.MatchScannerServiceActor;
import controllers.validation.BusinessValidator;
import controllers.validation.EmptyValidator;
import controllers.validation.ValidationResult;
import models.MatchRepository;
import models.PredictorRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.libs.Akka;
import play.mvc.Action;
import play.mvc.Http;
import scala.concurrent.duration.Duration;
import utils.AkkaUtils;
import utils.BadRequestAction;
import utils.BusinessLogic;
import utils.PredictorSecurity;
import utils.dev.InitialDataManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Application wide behaviour.
 * We establish a Spring application context for the dependency injection system and configure Spring Data.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class Global extends GlobalSettings {

    private static final String MATCH_SCANNER_MESSAGE = "scan";

    /**
     * Declare the application context to be used - a Java annotation based application context requiring no XML.
     */
    private final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

    /**
     * Initial data manager (dev mode only).
     */
    private final InitialDataManager initialDataManager = new InitialDataManager();

    /**
     * Predictor security util.
     */
    private PredictorSecurity predictorSecurity;

    /**
     * Current request business logic.
     */
    private BusinessLogic currentLogic;

    /**
     * Sync the context lifecycle with Play's.
     */
    @Override
    public void onStart(final Application app) {
        super.onStart(app);

        // AnnotationConfigApplicationContext can only be refreshed once, but we do it here even though this method
        // can be called multiple times. The reason for doing during startup is so that the Play configuration is
        // entirely available to this application context.
        ctx.register(SpringDataJpaConfiguration.class);
        ctx.scan("controllers", "models");
        ctx.refresh();

        // This will construct the beans and call any construction lifecycle methods e.g. @PostConstruct
        ctx.start();

        if (Play.isDev()) {
            Logger.debug("[DEV] Loading initial data ...");
            JPA.withTransaction(() -> {
                initialDataManager.dropData();
                initialDataManager.inserData();
            });
            Logger.debug("[DEV] Initial data loaded.");
        }

        Props props = Props.create(MatchScannerServiceActor.class, ctx.getBean(MatchRepository.class));
        ActorRef actor = Akka.system().actorOf(props);
        AkkaUtils.scheduleActor(actor, Duration.create(10, TimeUnit.SECONDS), MATCH_SCANNER_MESSAGE);
    }

    /**
     * Sync the context lifecycle with Play's.
     */
    @Override
    public void onStop(final Application app) {

        if (Play.isDev()) {
            Logger.debug("[DEV] Dropping data ...");
            JPA.withTransaction(() -> initialDataManager.dropData());
        }

        // This will call any destruction lifecycle methods and then release the beans e.g. @PreDestroy
        ctx.close();

        super.onStop(app);
    }

    @Override
    public Action onRequest(Http.Request request, Method method) {
        if (!method.isAnnotationPresent(BusinessLogic.class)) {
            return super.onRequest(request, method);
        }

        currentLogic = method.getAnnotation(BusinessLogic.class);
        if (!currentLogic.authenticate() && currentLogic.validator().equals(EmptyValidator.class)) {
            return super.onRequest(request, method);
        }

        if (!currentLogic.authenticate()) {
            return validate(request, method);
        }

        if (predictorSecurity == null) {
            predictorSecurity = new PredictorSecurity(ctx.getBean(PredictorRepository.class));
        }

        PredictorSecurity.Status status = predictorSecurity.authenticateRequest(request);
        if (status != PredictorSecurity.Status.SUCCESS) {
            return BadRequestAction.fromAuthenticationStatus(status);
        }

        if (currentLogic.validator().equals(EmptyValidator.class)) {
            return super.onRequest(request, method);
        }

        return validate(request, method);
    }

    private Action validate(Http.Request request, Method method) {
        BusinessValidator validator = ctx.getBean(currentLogic.validator());
        ValidationResult result =  validator.validate(request, currentLogic.validationContext());
        return result.success() ? super.onRequest(request, method) : BadRequestAction.fromValidationResult(result);
    }

    /**
     * Controllers must be resolved through the application context. There is a special method of GlobalSettings
     * that we can override to resolve a given controller. This resolution is required by the Play router.
     */
    @Override
    public <A> A getControllerInstance(Class<A> aClass) {
        return ctx.getBean(aClass);
    }

    /**
     * This configuration establishes Spring Data concerns including those of JPA.
     */
    @Configuration
    @EnableJpaRepositories("models")
    public static class SpringDataJpaConfiguration {

        /**
         * The name of the persistence unit we will be using.
         */
        private static final String PERSISTENCE_UNIT_NAME = "PREDICTOR_PU";

        @Bean
        public EntityManagerFactory entityManagerFactory() {
            return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }

        @Bean
        public HibernateExceptionTranslator hibernateExceptionTranslator() {
            return new HibernateExceptionTranslator();
        }

        @Bean
        public JpaTransactionManager transactionManager() {
            return new JpaTransactionManager();
        }
    }
}
