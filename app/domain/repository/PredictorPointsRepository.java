package domain.repository;

import domain.entity.PredictorPoints;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Predictor points repository.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public interface PredictorPointsRepository extends PredictorCrudRepository<PredictorPoints> {

    @Query("SELECT p FROM PredictorPoints p WHERE p.competition.id = :c_id AND p.predictor.id = :p_id")
    PredictorPoints findByCompetitionAndPredictor(@Param("c_id") Long competitionid, @Param("p_id") Long predictorId);
}
