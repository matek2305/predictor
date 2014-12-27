package controllers;

import models.CompetitionRepository;
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
        List<CompetitionTableRow> content = competitionRepository.findTableRowsById(competitionId);
        return ok(Json.toJson(content));
    }
}
