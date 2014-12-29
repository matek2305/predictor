package controllers;

import controllers.validation.MakePredictionValidator;
import models.MatchRepository;
import models.Prediction;
import models.PredictionRepository;
import models.dto.PredictionDetails;
import play.libs.Json;
import play.mvc.Result;
import utils.BusinessLogic;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class PredictionServices extends PredictorServicesController {

    @Inject
    private PredictionRepository predictionRepository;

    @Inject
    private MatchRepository matchRepository;

    @BusinessLogic(validator = MakePredictionValidator.class)
    public Result makePrediction() {
        PredictionDetails predictionDetails = prepareRequest(PredictionDetails.class);
        Prediction prediction = new Prediction();
        prediction.match = matchRepository.findOne(predictionDetails.getMatchId());
        prediction.predictor = getCurrentUser();
        prediction.homeTeamScore = predictionDetails.getHomeTeamScore();
        prediction.awayTeamScore = predictionDetails.getAwayTeamScore();
        return created(Json.toJson(predictionRepository.save(prediction)));
    }
}
