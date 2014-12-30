package models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * Repository for {@link models.Prediction} entity.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public interface PredictionRepository extends PredictorCrudRepository<Prediction> {

    @Query("SELECT p FROM Prediction p WHERE p.match.id = :matchId AND p.predictor.id = :predictorId")
    Optional<Prediction> findByMatchAndPredictor(@Param("matchId") Long matchId, @Param("predictorId") Long predictorId);
}
