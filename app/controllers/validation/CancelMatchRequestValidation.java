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
            addMessage("mecz o podanym identyfikatorze [%s] nie istnieje", getInputData().matchId);
            return;
        }

        if (match.status == Match.Status.CANCELLED) {
            addMessage("mecz został już anulowany");
            return;
        }
    }
}
