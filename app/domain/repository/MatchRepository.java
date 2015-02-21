package domain.repository;

import domain.entity.Match;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Date;
import java.util.List;

/**
 * Created by Hatake on 2014-12-13.
 */
@Named
@Singleton
public interface MatchRepository extends PredictorCrudRepository<Match> {

    List<Match> findByStatusAndStartDateLessThan(Match.Status status, Date date);

    @Query("SELECT m FROM Match m JOIN m.competition.predictorsPoints pp WHERE m.status = :status AND pp.predictor.id = :predictorId ORDER BY m.startDate ASC")
    List<Match> findByStatusAndPredictorOrderByStartDateAsc(@Param("status") Match.Status status, @Param("predictorId") Long predictorId, Pageable pageable);

    @Query("SELECT m FROM Match m WHERE m.status = :status AND m.competition.admin.id = :adminId ORDER BY m.startDate ASC")
    List<Match> findByStatusAndAdminOrderByStartDateAsc(@Param("status") Match.Status status, @Param("adminId") Long adminId, Pageable pageable);
}
