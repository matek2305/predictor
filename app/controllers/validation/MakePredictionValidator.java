package controllers.validation;

import models.PredictionRepository;
import models.dto.PredictionDetails;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
public class MakePredictionValidator extends AbstractBusinessValidator<PredictionDetails> {

    @Inject
    private PredictionRepository predictionRepository;

    @Override
    protected void validationLogic() {
        predictionRepository.findByMatchAndPredictor(getInputData().getMatchId(), getCurrentUser().id).ifPresent(
                p -> addMessage(getCurrentUser().login, "wynik meczu został dodany wcześniej (zaktualizuj wynik)")
        );
    }
}
