package controllers;

import controllers.validation.JoinCompetitionValidator;
import models.*;
import models.dto.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.BusinessLogic;
import utils.PredictorSecurity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
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

    /**
     * Create competition service.
     * @return
     */
    @BusinessLogic
    public Result createCompetition() {
        CompetitionDetails competitionDetails = prepareRequest(CompetitionDetails.class);
        Competition competition = new Competition(competitionDetails);
        competition.admin = getCurrentUser();
        competition.securityCode = PredictorSecurity.generateCompetitionCode();

        for (MatchDetails matchDetails : competitionDetails.matches) {
            Match match = new Match(matchDetails);
            match.status = Match.Status.OPEN_FOR_PREDICTION;
            match.competition = competition;

            if (competition.matches == null) {
                competition.matches = new ArrayList<>();
            }

            competition.matches.add(match);
        }

        competition = competitionRepository.save(competition);

        PredictorPoints predictorPoints = new PredictorPoints();
        predictorPoints.competition = competition;
        predictorPoints.predictor = getCurrentUser();
        predictorPointsRepository.save(predictorPoints);

        return created(new CompetitionWithSecurityCode(competition));
    }
}
