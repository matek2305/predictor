package controllers;

import controllers.validation.CancelMatchRequestValidation;
import controllers.validation.ExtendedMatchDetailsValidation;
import controllers.validation.MatchResultDataValidation;
import controllers.validation.ValidationContext;
import models.*;
import models.dto.CancelMatchRequest;
import models.dto.ExtendedMatchDetails;
import models.dto.MatchResultData;
import play.mvc.Result;
import utils.BusinessLogic;
import utils.MatchUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;

/**
 * Match services controller.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class MatchServices extends PredictorServicesController {

    private static final int CANCELLED_MATCH_POINTS = 0;
    private static final int CANCELLED_MATCH_TEAM_SCORE = -1;

    @Inject
    private MatchRepository matchRepository;

    @Inject
    private PredictorPointsRepository predictorPointsRepository;

    @Inject
    private CompetitionRepository competitionRepository;

    @BusinessLogic(validator = MatchResultDataValidation.class)
    public Result result() {
        MatchResultData data = prepareRequest(MatchResultData.class);
        Match match = matchRepository.findOne(data.matchId);
        match.homeTeamScore = data.homeTeamScore;
        match.awayTeamScore = data.awayTeamScore;
        match.status = Match.Status.RESULT_AVAILABLE;

        for (Prediction prediction : match.predictions) {
            prediction.points = MatchUtils.calculatePoints(match, prediction);

            PredictorPoints predictorPoints = predictorPointsRepository.findByCompetitionAndPredictor(match.competition.id, prediction.predictor.id);
            predictorPoints.points += prediction.points;
            predictorPointsRepository.save(predictorPoints);
        }

        return ok(matchRepository.save(match));
    }

    @BusinessLogic(validator = MatchResultDataValidation.class, validationContext = ValidationContext.MATCH_RESULT_CHANGE)
    public Result changeResult() {
        MatchResultData data = prepareRequest(MatchResultData.class);
        Match match = matchRepository.findOne(data.matchId);
        match.homeTeamScore = data.homeTeamScore;
        match.awayTeamScore = data.awayTeamScore;

        for (Prediction prediction : match.predictions) {
            int subtract = prediction.points;
            prediction.points = MatchUtils.calculatePoints(match, prediction);

            PredictorPoints predictorPoints = predictorPointsRepository.findByCompetitionAndPredictor(match.competition.id, prediction.predictor.id);
            predictorPoints.points -= subtract;
            predictorPoints.points += prediction.points;
            predictorPointsRepository.save(predictorPoints);
        }

        return ok(matchRepository.save(match));
    }

    @BusinessLogic(validator = CancelMatchRequestValidation.class)
    public Result cancel() {
        CancelMatchRequest request = prepareRequest(CancelMatchRequest.class);
        Match match = matchRepository.findOne(request.matchId);

        if (match.status == Match.Status.RESULT_AVAILABLE) {
            for (Prediction prediction : match.predictions) {
                int subtract = prediction.points;
                prediction.points = CANCELLED_MATCH_POINTS;

                PredictorPoints predictorPoints = predictorPointsRepository.findByCompetitionAndPredictor(match.competition.id, prediction.predictor.id);
                predictorPoints.points -= subtract;
                predictorPointsRepository.save(predictorPoints);
            }
        }

        match.homeTeamScore = CANCELLED_MATCH_TEAM_SCORE;
        match.awayTeamScore = CANCELLED_MATCH_TEAM_SCORE;
        match.status = Match.Status.CANCELLED;
        match.comments = request.comments;
        return ok(matchRepository.save(match));
    }

    @BusinessLogic(validator = ExtendedMatchDetailsValidation.class)
    public Result create() {
        ExtendedMatchDetails details = prepareRequest(ExtendedMatchDetails.class);
        Match match = new Match(details);
        match.status = Match.Status.OPEN_FOR_PREDICTION;
        match.competition = competitionRepository.findOne(details.competitionId);;
        return created(matchRepository.save(match));
    }
}
