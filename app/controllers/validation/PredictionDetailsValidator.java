package controllers.validation;

import models.Match;
import models.MatchRepository;
import models.PredictionRepository;
import models.dto.PredictionDetails;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Validator for {@link models.dto.PredictionDetails} data.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
public class PredictionDetailsValidator extends AbstractBusinessValidator<PredictionDetails> {

    @Inject
    private PredictionRepository predictionRepository;

    @Inject
    private MatchRepository matchRepository;

    @Override
    protected void validationLogic() {
        Match match = matchRepository.findOne(getInputData().getMatchId());
        if (match == null) {
            addMessage(getCurrentUser().login, "mecz o podanym identyfikatorzenie nie istnieje");
            return;
        }

        if (getCurrentUser().points.stream().map(p -> p.competition.id).noneMatch(id -> id.equals(match.competition.id))) {
            addMessage(getCurrentUser().login, "nie można typować wyniku meczu z turnieju w którym nie uczesnticzysz");
            return;
        }

        if (match.status != Match.Status.OPEN_FOR_PREDICTION) {
            addMessage(getCurrentUser().login, "typowanie wyniku zakończone");
            return;
        }

        if (getValidationContext() == ValidationContext.NEW_PREDICTION && predictionRepository.findByMatchAndPredictor(getInputData().getMatchId(), getCurrentUser().id).isPresent()) {
            addMessage(getCurrentUser().login, "wynik meczu został wytypowany wcześniej (zaktualizuj przy użyciu metody PUT)");
            return;
        }
    }
}
