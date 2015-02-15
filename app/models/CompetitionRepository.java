package models;

import models.dto.CompetitionTableRow;
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

    @Query("SELECT NEW models.dto.CompetitionTableRow(pp.predictor.login, pp.points) FROM PredictorPoints pp WHERE pp.competition.id = :id ORDER BY pp.points DESC")
    List<CompetitionTableRow> findTableRowsById(@Param("id") Long id);

    @Query("SELECT c FROM Competition c JOIN c.predictorsPoints pp WHERE pp.predictor.id = :predictorId ORDER BY pp.points DESC")
    List<Competition> findForPredictorOrderByPointsDesc(@Param("predictorId") Long predictorId, Pageable pageable);
}
