import controllers.validation.BusinessValidator;
import controllers.validation.Validate;
import controllers.validation.ValidationResult;
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
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;
import utils.dev.InitialDataManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Application wide behaviour. We establish a Spring application context for the dependency injection system and
 * configure Spring Data.
 */
public class Global extends GlobalSettings {

    /**
     * Declare the application context to be used - a Java annotation based application context requiring no XML.
     */
    private final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

    /**
     * Initial data manager (dev mode only).
     */
    private final InitialDataManager initialDataManager = new InitialDataManager();

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
        if (method.isAnnotationPresent(Validate.class)) {
            Validate annotation = method.getAnnotation(Validate.class);
            BusinessValidator validator = ctx.getBean(annotation.value());
            if (!validator.validate(Json.fromJson(request.body().asJson(), validator.getInputDataClass()))) {
                return ValidationFailed.fromResult(validator.getResult());
            }
        }
        return super.onRequest(request, method);
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

    /**
     * Validation failed action.
     */
    public static class ValidationFailed extends Action {

        public static ValidationFailed fromResult(ValidationResult result) {
            return new ValidationFailed(result);
        }

        private ValidationResult result;

        private ValidationFailed(ValidationResult result) {
            this.result = result;
        }

        @Override
        public F.Promise<SimpleResult> call(Http.Context context) throws Throwable {
            return F.Promise.pure((SimpleResult) badRequest(Json.toJson(result)));
        }
    }
}
