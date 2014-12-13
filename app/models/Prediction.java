package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
public class Prediction extends AbstractPredictorEntity {

    /**
     * Home team score prediction.
     */
    @NotNull
    public int homeTeamScore;

    /**
     * Away team score prediction.
     */
    @NotNull
    public int awayTeamScore;

    /**
     * Points for this prediction.
     */
    public int points;

    /**
     * Prediction target match.
     */
    @ManyToOne(optional = false)
    public Match match;

    /**
     * Predicting user.
     */
    @ManyToOne(optional = false)
    public Predictor predictor;
}
