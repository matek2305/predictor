package models;

import models.dto.CompetitionTableRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 * Created by Hatake on 2014-12-13.
 */
@Named
@Singleton
public interface CompetitionRepository extends PredictorCrudRepository<Competition> {

    @Query("SELECT NEW models.dto.CompetitionTableRow(pp.predictor.login, pp.points) FROM PredictorPoints pp WHERE pp.competition.id = :id ORDER BY pp.points DESC")
    List<CompetitionTableRow> findTableRowsById(@Param("id") Long id);
}
