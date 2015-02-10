package controllers;

import controllers.validation.RegisterUserValidator;
import models.Predictor;
import models.dto.AuthenticateUserResponse;
import models.dto.PredictorDetails;
import play.libs.Json;
import play.mvc.Result;
import utils.BadRequestAction;
import utils.BusinessLogic;
import utils.PredictorSecurity;

import javax.inject.Named;
import javax.inject.Singleton;
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
        PredictorDetails predictorDetails = prepareRequest(PredictorDetails.class);
        Predictor user = new Predictor();
        user.login = predictorDetails.login;
        user.password = predictorDetails.password;
        user.registrationDate = new Date();
        return created(getPredictorRepository().save(user));
    }

    /**
     * User authentication service.
     * @return authentication token
     */
    @BusinessLogic(authenticate = false)
    public Result autheticateUser() {
        PredictorDetails predictorDetails = prepareRequest(PredictorDetails.class);
        Optional<Predictor> predictor = getPredictorRepository().findByPredictorDetails(predictorDetails);
        if (predictor.isPresent()) {
            predictor.get().authenticationToken = PredictorSecurity.generateToken();
            predictor.get().tokenExpirationDate = PredictorSecurity.calculateTokenExpirationDate();
            Predictor authenticatedPredictor = getPredictorRepository().save(predictor.get());
            return created(new AuthenticateUserResponse(authenticatedPredictor));
        }

        response().setHeader(BadRequestAction.PREDICTOR_STATUS_REASON_HEADER, PredictorSecurity.Status.FAILED.getStatusReasonHeaderValue());
        return badRequest();
    }

    /**
     * End user session.
     * @return
     */
    @BusinessLogic
    public Result endSession() {
        Predictor user = getCurrentUser();
        user.tokenExpirationDate = new Date();
        getPredictorRepository().save(user);
        return ok();
    }
}
