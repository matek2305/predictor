package controllers.validation;

import models.Match;
import models.MatchRepository;
import models.dto.CancelMatchRequest;
import utils.MatchUtils;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
public class CancelMatchRequestValidation extends AbstractBusinessValidator<CancelMatchRequest> {

    @Inject
    private MatchRepository matchRepository;

    @Override
    protected void validationLogic() {
        Match match = matchRepository.findOne(getInputData().matchId);
        if (match == null) {
            addMessage(getInputData().matchId.toString(), "mecz o podanym identyfikatorze nie istnieje");
            return;
        }

        if (match.status == Match.Status.CANCELLED) {
            addMessage(MatchUtils.getMatchLabel(match), "mecz został już anulowany");
            return;
        }
    }
}
