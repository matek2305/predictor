package controllers.validation;

import models.Match;
import models.MatchRepository;
import models.dto.MatchResultData;
import utils.MatchUtils;

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
            addMessage(getInputData().matchId.toString(), "mecz o podanym identyfikatorze nie istnieje");
            return;
        }

        if (!match.competition.admin.id.equals(getCurrentUser().id)) {
            addMessage(match.competition.name, "tylko administrator turnieju może uzupełniać wyniki meczów");
            return;
        }

        if (getValidationContext() == ValidationContext.DEFAULT && match.status == Match.Status.RESULT_AVAILABLE) {
            addMessage(MatchUtils.getMatchLabel(match), "wynik został już uzupełniony");
            return;
        }

        if (getValidationContext() == ValidationContext.MATCH_RESULT_CHANGE && match.status != Match.Status.RESULT_AVAILABLE) {
            addMessage(MatchUtils.getMatchLabel(match), "nie znaleziono wyniku do aktualizacji, uzupełnij wynik");
            return;
        }

        if (getInputData().homeTeamScore < 0 || getInputData().awayTeamScore < 0) {
            addMessage(MatchUtils.getMatchLabel(match), "nieprawidłowy wynik");
            return;
        }
    }
}
