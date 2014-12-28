package controllers.validation;

import models.Predictor;
import models.PredictorRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Hatake on 2014-12-25.
 */
@Named
public class RegisterUserValidator extends AbstractBusinessValidator<Predictor> {

    @Inject
    private PredictorRepository predictorRepository;

    @Override
    protected void validationLogic(Predictor predictor) {
        if (predictorRepository.findByLogin(predictor.login).isPresent()) {
            addMessage(predictor.login, "nazwa użytkownika jest już zajęta");
        }
    }
}
