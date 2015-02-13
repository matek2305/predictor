package controllers.validation;

import models.Competition;
import models.CompetitionRepository;
import models.Match;
import models.dto.ExtendedMatchDetails;
import utils.MatchUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
public class ExtendedMatchDetailsValidation extends AbstractBusinessValidator<ExtendedMatchDetails> {

    @Inject
    private CompetitionRepository competitionRepository;

    @Override
    protected void validationLogic() {
        Competition competition = competitionRepository.findOne(getInputData().competitionId);
        if (competition == null) {
            addMessage("turniej o podanym identyfikatorze [%s] nie istnieje", getInputData().competitionId);
            return;
        }

        if (competition.admin.id != getCurrentUser().id) {
            addMessage("tylko adminitrator może dodawać mecze");
            return;
        }

        if (getInputData().startDate.getTime() < new Date().getTime()) {
            addMessage("można dodawac tylko może które się odbędą");
        }

        for (Match match : competition.matches) {
            if (MatchUtils.equals(match, getInputData())) {
                addMessage("ten mecz już został dodany do turnieju");
                return;
            }
        }
    }
}
