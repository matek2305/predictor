package domain.entity;

import utils.RandomIdGenerator;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by Hatake on 2014-12-13.
 */
public class PredictorEntitiesListener {

    @PrePersist
    public void onPersist(AbstractPredictorEntity entity) {
        entity.creationDate = new Date();
        entity.lastUpdateDate = new Date();

        if (entity.id == null) {
            entity.id = RandomIdGenerator.generate();
        }
    }

    @PreUpdate
    public void onUpdate(AbstractPredictorEntity entity) {
        entity.lastUpdateDate = new Date();
    }
}
