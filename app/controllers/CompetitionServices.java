package controllers;

import controllers.validation.JoinCompetitionValidator;
import models.Competition;
import models.CompetitionRepository;
import models.PredictorPoints;
import models.PredictorPointsRepository;
import models.dto.CompetitionTable;
import models.dto.CompetitionTableRow;
import models.dto.JoinCompetitionRequest;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.BusinessLogic;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 * Competition services controller.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class CompetitionServices extends PredictorServicesController {

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private PredictorPointsRepository predictorPointsRepository;

    /**
     * Current competition table service.
     * @param competitionId competition id
     * @return current standings
     */
    @BusinessLogic
    public Result buildTable(Long competitionId) {
        Competition competition = competitionRepository.findOne(competitionId);
        if (competition == null) {
            return notFound("Competition not found!");
        }

        List<CompetitionTableRow> content = competitionRepository.findTableRowsById(competitionId);
        CompetitionTable table = new CompetitionTable(competition.name, content);
        return ok(table);
    }

    /**
     * Join competition service.
     * @return
     */
    @BusinessLogic(validator = JoinCompetitionValidator.class)
    public Result joinCompetition() {
        JoinCompetitionRequest request = prepareRequest(JoinCompetitionRequest.class);
        PredictorPoints predictorPoints = new PredictorPoints();
        predictorPoints.competition = competitionRepository.findOne(request.competitionId);
        predictorPoints.predictor = getCurrentUser();
        return created(predictorPointsRepository.save(predictorPoints));
    }
}
