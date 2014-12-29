package controllers;

import controllers.validation.RegisterUserValidator;
import models.Predictor;
import models.PredictorRepository;
import models.dto.AuthenticationDetails;
import org.apache.commons.lang3.RandomStringUtils;
import play.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * Basic services controller.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class PredictorServices extends PredictorServicesController {

    public Result index() {
        return ok(views.html.index.render("Predictor"));
    }

    /**
     * New user registration service.
     * @return created user
     */
    @BusinessLogic(validator = RegisterUserValidator.class, authenticate = false)
    public Result registerUser() {
        Predictor user = Json.fromJson(request().body().asJson(), Predictor.class);
        user.registrationDate = new Date();
        return created(Json.toJson(getPredictorRepository().save(user)));
    }

    /**
     * User authentication service.
     * @return authentication token
     */
    @BusinessLogic(authenticate = false)
    public Result autheticateUser() {
        AuthenticationDetails authenticationDetails = Json.fromJson(request().body().asJson(), AuthenticationDetails.class);
        Optional<Predictor> predictor = getPredictorRepository().findByLoginAndPassword(authenticationDetails);
        if (predictor.isPresent()) {
            predictor.get().authenticationToken = PredictorSecurity.generateToken();
            predictor.get().tokenExpirationDate = PredictorSecurity.calculateTokenExpirationDate();
            return created(getPredictorRepository().save(predictor.get()).authenticationToken);
        }

        response().setHeader(BadRequestAction.PREDICTOR_STATUS_REASON_HEADER, PredictorSecurity.Status.FAILED.getStatusReasonHeaderValue());
        return badRequest();
    }
}
