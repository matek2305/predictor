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
            addMessage(getInputData().competitionId.toString(), "turniej o podanym identyfikatorze nie istnieje");
            return;
        }

        if (competition.admin.id != getCurrentUser().id) {
            addMessage(competition.name, "tylko adminitrator może dodawać mecze");
            return;
        }

        if (getInputData().startDate.getTime() < new Date().getTime()) {
            addMessage(MatchUtils.getMatchLabel(getInputData()), "można dodawac tylko może które się odbędą");
        }

        for (Match match : competition.matches) {
            if (MatchUtils.equals(match, getInputData())) {
                addMessage(MatchUtils.getMatchLabel(getInputData()), "ten mecz już został dodany do turnieju");
                return;
            }
        }
    }
}
