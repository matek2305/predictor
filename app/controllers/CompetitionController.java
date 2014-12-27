package controllers;

import models.Competition;
import models.CompetitionRepository;
import models.dto.CompetitionTable;
import models.dto.CompetitionTableRow;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 * Created by Hatake on 2014-12-27.
 */
@Named
@Singleton
public class CompetitionController extends Controller {

    @Inject
    private CompetitionRepository competitionRepository;

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
