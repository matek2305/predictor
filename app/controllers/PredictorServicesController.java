package controllers;

import models.Predictor;
import models.PredictorRepository;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;
import play.mvc.Controller;

import javax.inject.Inject;

/**
 * Base predictor services controller.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public abstract class PredictorServicesController extends Controller {

    @Inject
    private PredictorRepository predictorRepository;

    protected Predictor getCurrentUser() {
        if (StringUtils.isBlank(request().username())) {
            throw new IllegalStateException("getCurrentUser() called with no username in request!");
        }
        
        return predictorRepository.findByLogin(request().username()).get();
    }

    protected <T> T prepareRequest(Class<T> type) {
        return Json.fromJson(request().body().asJson(), type);
    }

    protected PredictorRepository getPredictorRepository() {
        return predictorRepository;
    }
}
