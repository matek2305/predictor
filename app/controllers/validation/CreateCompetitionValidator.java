package controllers.validation;

import domain.repository.CompetitionRepository;
import domain.dto.CompetitionDetails;
import domain.dto.MatchDetails;
import utils.MatchUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

/**
 * Validator for {@link controllers.CompetitionServices#createCompetition()} business logic.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
public class CreateCompetitionValidator extends AbstractBusinessValidator<CompetitionDetails> {

    @Inject
    private CompetitionRepository competitionRepository;

    @Override
    protected void validationLogic() {
        if (competitionRepository.findByName(getInputData().name).isPresent()) {
            addMessage("turniej o tej nazwie już istnieje");
            return;
        }

        for (MatchDetails matchDetails : getInputData().matches) {
            if (matchDetails.startDate.getTime() < new Date().getTime()) {
                addMessage("mecz ma datę rozpoczęcia z przeszłości");
                return;
            }
        }

        for (int i = 0; i < getInputData().matches.size(); i++) {

            MatchDetails currentMatch = getInputData().matches.get(i);
            if (currentMatch.startDate.getTime() < new Date().getTime()) {
                addMessage("mecz ma datę rozpoczęcia z przeszłości");
                return;
            }

            for (int j = i + 1; j < getInputData().matches.size(); j++) {
                if (MatchUtils.equals(currentMatch, getInputData().matches.get(j))) {
                    addMessage("podano dwa takie same mecze");
                    return;
                }
            }
        }
    }
}
