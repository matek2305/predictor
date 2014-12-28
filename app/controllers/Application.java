package controllers;

import controllers.validation.RegisterUserValidator;
import controllers.validation.Validate;
import models.Predictor;
import models.PredictorRepository;
import models.dto.AuthenticationDetails;
import org.apache.commons.lang3.RandomStringUtils;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Date;
import java.util.Optional;

/**
 * The main set of web services.
 */
@Named
@Singleton
public class Application extends Controller {

    public static final String PREDICTOR_STATUS_REASON_HEADER = "Predictor-Status-Reason";
    public static final String AUTHENTICATION_FAILED = "Authentication failed";
    public static final String VALIDATION_FAILED = "Validation failed";

    private static final long ONE_MINUTE_IN_MILLIS = 60000;
    private static final int TOKEN_EXP_TIME_IN_MINUTES = 15;

    @Inject
    private PredictorRepository predictorRepository;

    public Result index() {
        return ok(views.html.index.render("Predictor"));
    }

    @Validate(RegisterUserValidator.class)
    public Result registerUser() {
        Predictor user = Json.fromJson(request().body().asJson(), Predictor.class);
        user.registrationDate = new Date();
        return created(Json.toJson(predictorRepository.save(user)));
    }

    public Result autheticateUser() {
        AuthenticationDetails authenticationDetails = Json.fromJson(request().body().asJson(), AuthenticationDetails.class);
        Optional<Predictor> predictor = predictorRepository.findByLoginAndPassword(authenticationDetails);
        if (predictor.isPresent()) {
            predictor.get().authenticationToken = RandomStringUtils.randomAlphanumeric(32);

            // TODO: change to java 8 datetime api
            long currentTimeInMillis = new Date().getTime();
            predictor.get().tokenExpirationDate = new Date(currentTimeInMillis + (TOKEN_EXP_TIME_IN_MINUTES * ONE_MINUTE_IN_MILLIS));

            return created(predictorRepository.save(predictor.get()).authenticationToken);
        }

        response().setHeader(PREDICTOR_STATUS_REASON_HEADER, AUTHENTICATION_FAILED);
        return badRequest();
    }
}
