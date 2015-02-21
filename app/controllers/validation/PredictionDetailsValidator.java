package controllers.validation;

import domain.entity.Match;
import domain.repository.MatchRepository;
import domain.repository.PredictionRepository;
import domain.dto.PredictionDetails;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Validator for {@link domain.dto.PredictionDetails} data.
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
        Match match = matchRepository.findOne(getInputData().matchId);
        if (match == null) {
            addMessage("mecz o podanym identyfikatorzenie [%s] nie istnieje", getInputData().matchId);
            return;
        }

        if (getCurrentUser().points.stream().map(p -> p.competition.id).noneMatch(id -> id.equals(match.competition.id))) {
            addMessage("nie można typować wyniku meczu z turnieju w którym nie uczesnticzysz");
            return;
        }

        if (match.status != Match.Status.OPEN_FOR_PREDICTION) {
            addMessage("typowanie wyniku zakończone");
            return;
        }

        if (getValidationContext() == ValidationContext.NEW_PREDICTION && predictionRepository.findByMatchAndPredictor(getInputData().matchId, getCurrentUser().id).isPresent()) {
            addMessage("wynik meczu został wytypowany wcześniej (zaktualizuj przy użyciu metody PUT)");
            return;
        }
    }
}
