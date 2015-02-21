package domain.repository;

import domain.entity.AbstractPredictorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Hatake on 2014-12-13.
 */
@NoRepositoryBean
public interface PredictorCrudRepository<T extends AbstractPredictorEntity> extends CrudRepository<T, Long> {
}
