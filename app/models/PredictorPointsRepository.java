package models;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Predictor points repository.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public interface PredictorPointsRepository extends PredictorCrudRepository<PredictorPoints> {
}
