package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Hatake on 2014-12-13.
 */
@MappedSuperclass
@EntityListeners(PredictorEntitiesListener.class)
public abstract class AbstractPredictorEntity {

    /**
     * ID.
     */
    @Id
    public Long id;

    /**
     * Entity creation datetime.
     */
    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date creationDate;

    /**
     * Entity last update datetime.
     */
    @Column(name = "last_update_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date lastUpdateDate;
}