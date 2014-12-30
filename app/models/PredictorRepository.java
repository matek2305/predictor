package models;

import models.dto.PredictorDetails;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public interface PredictorRepository extends PredictorCrudRepository<Predictor> {

    Optional<Predictor> findByLogin(String login);

    Optional<Predictor> findByLoginAndPassword(String login, String password);

    Optional<Predictor> findByAuthenticationToken(String token);

    default Optional<Predictor> findByPredictorDetails(PredictorDetails predictorDetails) {
        return findByLoginAndPassword(predictorDetails.login, predictorDetails.password);
    }
}
