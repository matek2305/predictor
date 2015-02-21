package controllers;

import controllers.validation.PredictionDetailsValidator;
import controllers.validation.ValidationContext;
import domain.repository.MatchRepository;
import domain.entity.Prediction;
import domain.repository.PredictionRepository;
import domain.dto.PredictionDetails;
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
public class PredictionServices extends CommonPedictorService {

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
        Optional<Prediction> prediction = predictionRepository.findByMatchAndPredictor(predictionDetails.matchId, getCurrentUser().id);
        if (prediction.isPresent()) {
            prediction.get().homeTeamScore = predictionDetails.homeTeamScore;
            prediction.get().awayTeamScore = predictionDetails.awayTeamScore;
            return ok(predictionRepository.save(prediction.get()));
        }

        return created(createNew(predictionDetails));
    }

    private Prediction createNew(PredictionDetails details) {
        Prediction prediction = new Prediction();
        prediction.match = matchRepository.findOne(details.matchId);
        prediction.predictor = getCurrentUser();
        prediction.homeTeamScore = details.homeTeamScore;
        prediction.awayTeamScore = details.awayTeamScore;
        return predictionRepository.save(prediction);
    }
}
