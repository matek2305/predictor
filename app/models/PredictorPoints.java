package models;

import utils.dev.InitialData;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
@Table(name = "predictor_points")
@InitialData(name = "points", order = 4)
public class PredictorPoints extends AbstractPredictorEntity {

    /**
     * Predictor.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "predictor_id")
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
