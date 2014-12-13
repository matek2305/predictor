package models;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by Hatake on 2014-12-13.
 */
public class PredictorEntitiesListener {

    @PrePersist
    public void handleCreationDate(AbstractPredictorEntity entity) {
        entity.creationDate = new Date();
        entity.lastUpdateDate = new Date();
    }

    @PreUpdate
    public void handleLastUpdateDate(AbstractPredictorEntity entity) {
        entity.lastUpdateDate = new Date();
    }
}
