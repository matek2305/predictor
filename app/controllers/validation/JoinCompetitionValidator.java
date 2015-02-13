package controllers.validation;

import models.Competition;
import models.CompetitionRepository;
import models.dto.JoinCompetitionRequest;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Validator for {@link controllers.CompetitionServices#joinCompetition()} business logic.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
public class JoinCompetitionValidator extends AbstractBusinessValidator<JoinCompetitionRequest> {

    @Inject
    private CompetitionRepository competitionRepository;

    @Override
    protected void validationLogic() {
        Competition competition = competitionRepository.findOne(getInputData().competitionId);
        if (competition == null) {
            addMessage("turniej o podanym identyfikatorze nie istnieje");
            return;
        }

        if (!competition.securityCode.equals(getInputData().competitionCode)) {
            addMessage("nieprawidłowy kod bezpieczeństwa");
            return;
        }

        if (competition.predictorsPoints.stream().map(p -> p.predictor.id).anyMatch(id -> id.equals(getCurrentUser().id))) {
            addMessage("nie możesz dwa razy dołączyć do tego samego turnieju");
            return;
        }
    }
}
