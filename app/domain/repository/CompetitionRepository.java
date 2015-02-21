package domain.repository;

import domain.entity.Competition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

/**
 * Created by Hatake on 2014-12-13.
 */
@Named
@Singleton
public interface CompetitionRepository extends PredictorCrudRepository<Competition> {

    Optional<Competition> findByName(String name);

    @Query("SELECT c FROM Competition c JOIN c.predictorsPoints pp WHERE pp.predictor.id = :predictorId ORDER BY pp.points DESC")
    List<Competition> findForPredictorOrderByPointsDesc(@Param("predictorId") Long predictorId, Pageable pageable);

    @Query("SELECT c FROM Competition c JOIN c.predictorsPoints pp WHERE c.admin.id <> :predictorId AND pp.predictor.id = :predictorId")
    List<Competition> findForPredictorThatIsNotAdmin(@Param("predictorId") Long predictorId);

    @Query("SELECT c FROM Competition c WHERE c.admin.id = :adminId")
    List<Competition> findByAdmin(@Param("adminId") Long adminId);
}
