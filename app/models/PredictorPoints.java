package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
public class PredictorPoints extends AbstractPredictorEntity {

    /**
     * Predictor.
     */
    @NotNull
    @ManyToOne
    public Predictor predictor;

    /**
     * Competition.
     */
    @NotNull
    @ManyToOne
    public Competition competition;

    /**
     * Predictor points in specific competition.
     */
    @NotNull
    @Column(columnDefinition = "integer default 0")
    public int points;
}
