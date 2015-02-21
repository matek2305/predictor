package domain.entity;

import org.apache.commons.math3.random.RandomDataGenerator;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by Hatake on 2014-12-13.
 */
public class PredictorEntitiesListener {

    private static final RandomDataGenerator RANDOM_DATA_GENERATOR = new RandomDataGenerator();

    @PrePersist
    public void onPersist(AbstractPredictorEntity entity) {
        entity.creationDate = new Date();
        entity.lastUpdateDate = new Date();

        if (entity.id == null) {
            entity.id = RANDOM_DATA_GENERATOR.nextLong(1, Long.MAX_VALUE);
        }
    }

    @PreUpdate
    public void onUpdate(AbstractPredictorEntity entity) {
        entity.lastUpdateDate = new Date();
    }
}
