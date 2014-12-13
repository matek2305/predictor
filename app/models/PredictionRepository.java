package models;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by Hatake on 2014-12-13.
 */
@Named
@Singleton
public interface PredictionRepository extends PredictorCrudRepository<Prediction> {
}
