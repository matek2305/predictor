package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.dev.InitialData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
@Table(name = "prediction")
@InitialData(name = "predictions", order = 5)
public class Prediction extends AbstractPredictorEntity {

    /**
     * Home team score prediction.
     */
    @Column(name = "home_team_score", nullable = false)
    public int homeTeamScore;

    /**
     * Away team score prediction.
     */
    @Column(name = "away_team_score", nullable = false)
    public int awayTeamScore;

    /**
     * Points for this prediction.
     */
    @Column(columnDefinition = "integer default 0", nullable = false)
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
