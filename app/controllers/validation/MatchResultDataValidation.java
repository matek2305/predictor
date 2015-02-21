package controllers.validation;

import domain.entity.Match;
import domain.repository.MatchRepository;
import domain.dto.MatchResultData;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
public class MatchResultDataValidation extends AbstractBusinessValidator<MatchResultData> {

    @Inject
    private MatchRepository matchRepository;

    @Override
    protected void validationLogic() {
        Match match = matchRepository.findOne(getInputData().matchId);
        if (match == null) {
            addMessage("mecz o podanym identyfikatorze [%s] nie istnieje", getInputData().matchId);
            return;
        }

        if (!match.competition.admin.id.equals(getCurrentUser().id)) {
            addMessage("tylko administrator turnieju może uzupełniać wyniki meczów");
            return;
        }

        if (match.status == Match.Status.CANCELLED) {
            addMessage("mecz został anulowany");
            return;
        }

        if (getValidationContext() == ValidationContext.DEFAULT && match.status == Match.Status.RESULT_AVAILABLE) {
            addMessage("wynik został już uzupełniony");
            return;
        }

        if (getValidationContext() == ValidationContext.MATCH_RESULT_CHANGE && match.status != Match.Status.RESULT_AVAILABLE) {
            addMessage("nie znaleziono wyniku do aktualizacji, uzupełnij wynik");
            return;
        }

        if (getInputData().homeTeamScore < 0 || getInputData().awayTeamScore < 0) {
            addMessage("nieprawidłowy wynik");
            return;
        }
    }
}
