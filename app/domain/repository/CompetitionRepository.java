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
}
