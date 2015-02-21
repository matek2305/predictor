package controllers.validation;

import domain.entity.Predictor;

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
            addMessage("nazwa użytkownika jest już zajęta");
        }
    }
}
