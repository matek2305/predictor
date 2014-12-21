package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
@Table(name = "predictor_points")
public class PredictorPoints extends AbstractPredictorEntity {

    /**
     * Predictor.
     */
    @ManyToOne(optional = false)
    public Predictor predictor;

    /**
     * Competition.
     */
    @ManyToOne(optional = false)
    public Competition competition;

    /**
     * Predictor points in specific competition.
     */
    @Column(columnDefinition = "integer default 0", nullable = false)
    public int points;
}
