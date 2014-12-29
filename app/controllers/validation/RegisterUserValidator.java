package controllers.validation;

import models.Predictor;
import models.PredictorRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Validator for {@link controllers.PredictorServices#registerUser()} business logic.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
public class RegisterUserValidator extends AbstractBusinessValidator<Predictor> {

    @Override
    protected void validationLogic() {
        if (getPredictorRepository().findByLogin(getInputData().login).isPresent()) {
            addMessage(getInputData().login, "nazwa użytkownika jest już zajęta");
        }
    }
}
