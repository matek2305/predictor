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
        Competition competition = competitionRepository.findOne(getInputData().getCompetitionId());
        if (competition == null) {
            addMessage(getCurrentUser().login, "turniej o podanym identyfikatorze nie istnieje");
            return;
        }

        if (!competition.securityCode.equals(getInputData().getCompetitionCode())) {
            addMessage(getInputData().getCompetitionCode(), "nieprawidłowy kod bezpieczeństwa");
            return;
        }

        if (competition.predictorsPoints.stream().map(p -> p.predictor.id).anyMatch(id -> id.equals(getCurrentUser().id))) {
            addMessage(getCurrentUser().login, "nie możesz dwa razy dołączyć do tego samego turnieju");
            return;
        }
    }
}
