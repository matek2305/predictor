package controllers;

import controllers.validation.RegisterUserValidator;
import controllers.validation.Validate;
import models.Predictor;
import models.PredictorRepository;
import models.dto.AuthenticationDetails;
import org.apache.commons.lang3.RandomStringUtils;
import play.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.DateHelper;
import utils.PredictorSettings;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * Basic services controller.
 *
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class Application extends Controller {

    public static final String PREDICTOR_STATUS_REASON_HEADER = "Predictor-Status-Reason";
    public static final String AUTHENTICATION_FAILED = "Authentication failed";
    public static final String VALIDATION_FAILED = "Validation failed";

    private static final int AUTH_TOKEN_LENGTH = 32;

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
            predictor.get().authenticationToken = RandomStringUtils.randomAlphanumeric(AUTH_TOKEN_LENGTH);
            predictor.get().tokenExpirationDate = calculateTokenExpirationDate();
            return created(predictorRepository.save(predictor.get()).authenticationToken);
        }

        response().setHeader(PREDICTOR_STATUS_REASON_HEADER, AUTHENTICATION_FAILED);
        return badRequest();
    }

    private Date calculateTokenExpirationDate() {
        int expirationTime = Play.application().configuration().getInt(PredictorSettings.AUTH_TOKEN_EXPIRATION_DATE);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(expirationTime);
        return DateHelper.toDate(expirationDate);
    }
}
