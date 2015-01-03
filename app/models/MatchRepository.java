package models;

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
}
