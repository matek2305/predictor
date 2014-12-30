package controllers;

import controllers.validation.PredictionDetailsValidator;
import controllers.validation.ValidationContext;
import models.MatchRepository;
import models.Prediction;
import models.PredictionRepository;
import models.dto.PredictionDetails;
import play.mvc.Result;
import utils.BusinessLogic;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * Prediction services.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class PredictionServices extends PredictorServicesController {

    @Inject
    private PredictionRepository predictionRepository;

    @Inject
    private MatchRepository matchRepository;

    /**
     * Service for making prediction.
     * @return
     */
    @BusinessLogic(validator = PredictionDetailsValidator.class, validationContext = ValidationContext.NEW_PREDICTION)
    public Result makePrediction() {
        return created(createNew(prepareRequest(PredictionDetails.class)));
    }

    /**
     * Service for updating prediction.
     * @return
     */
    @BusinessLogic(validator = PredictionDetailsValidator.class)
    public Result updatePrediction() {
        PredictionDetails predictionDetails = prepareRequest(PredictionDetails.class);
        Optional<Prediction> prediction = predictionRepository.findByMatchAndPredictor(predictionDetails.getMatchId(), getCurrentUser().id);
        if (prediction.isPresent()) {
            prediction.get().homeTeamScore = predictionDetails.getHomeTeamScore();
            prediction.get().awayTeamScore = predictionDetails.getAwayTeamScore();
            return ok(predictionRepository.save(prediction.get()));
        }

        return created(createNew(predictionDetails));
    }

    private Prediction createNew(PredictionDetails details) {
        Prediction prediction = new Prediction();
        prediction.match = matchRepository.findOne(details.getMatchId());
        prediction.predictor = getCurrentUser();
        prediction.homeTeamScore = details.getHomeTeamScore();
        prediction.awayTeamScore = details.getAwayTeamScore();
        return predictionRepository.save(prediction);
    }
}
