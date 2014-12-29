package controllers;

import models.Competition;
import models.CompetitionRepository;
import models.dto.CompetitionTable;
import models.dto.CompetitionTableRow;
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
        return ok(Json.toJson(table));
    }
}
