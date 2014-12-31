package controllers;

import controllers.validation.FinishMatchValidator;
import models.*;
import models.dto.FinishMatchRequest;
import play.mvc.Result;
import utils.BusinessLogic;
import utils.MatchUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Match services controller.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class MatchServices extends PredictorServicesController {

    @Inject
    private MatchRepository matchRepository;

    @Inject
    private PredictorPointsRepository predictorPointsRepository;

    @BusinessLogic(validator = FinishMatchValidator.class)
    public Result finishMatch() {
        FinishMatchRequest request = prepareRequest(FinishMatchRequest.class);
        Match match = matchRepository.findOne(request.matchId);
        match.homeTeamScore = request.homeTeamScore;
        match.awayTeamScore = request.awayTeamScore;
        match.status = Match.Status.RESULT_AVAILABLE;

        for (Prediction prediction : match.predictions) {
            prediction.points = MatchUtils.calculatePoints(match, prediction);

            PredictorPoints predictorPoints = predictorPointsRepository.findByCompetitionAndPredictor(match.competition.id, prediction.predictor.id);
            predictorPoints.points += prediction.points;
            predictorPointsRepository.save(predictorPoints);
        }

        return ok(matchRepository.save(match));
    }
}
