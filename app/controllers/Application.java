package controllers;

import controllers.validation.RegisterUserValidator;
import controllers.validation.Validate;
import models.Predictor;
import models.PredictorRepository;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Date;

/**
 * The main set of web services.
 */
@Named
@Singleton
public class Application extends Controller {

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
}
