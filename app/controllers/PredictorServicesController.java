package controllers;

import models.Predictor;
import models.PredictorRepository;
import org.apache.commons.lang3.StringUtils;
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
            return null;
        }
        
        return predictorRepository.findByLogin(request().username()).orElse(null);
    }

    protected PredictorRepository getPredictorRepository() {
        return predictorRepository;
    }
}
