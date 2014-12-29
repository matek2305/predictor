package models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * Created by Hatake on 2014-12-13.
 */
@Named
@Singleton
public interface PredictionRepository extends PredictorCrudRepository<Prediction> {

    @Query("SELECT p FROM Prediction p WHERE p.match.id = :matchId AND p.predictor.id = :predictorId")
    Optional<Prediction> findByMatchAndPredictor(@Param("matchId") Long matchId, @Param("predictorId") Long predictorId);
}
