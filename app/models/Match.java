package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
@Table(name = "match")
public class Match extends AbstractPredictorEntity {

    /**
     * Home team name.
     */
    @NotNull
    public String homeTeamName;

    /**
     * Away team team;
     */
    @NotNull
    public String awayTeamName;

    /**
     * Home team score (-1 if not available).
     */
    @Column(columnDefinition = "integer default -1")
    public int homeTeamScore;

    /**
     * Away team score (-1 if not available).
     */
    @Column(columnDefinition = "integer default -1")
    public int awayTeamScore;

    /**
     * Start date.
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    public Date startDate;

    /**
     * Current status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    public Status status;

    /**
     * Competition.
     */
    @NotNull
    @ManyToOne
    public Competition competition;

    /**
     * Lists of predictions for this match.
     */
    @OneToMany
    public List<Prediction> predictions;

    /**
     * Match status.
     */
    public static enum Status {
        OPEN_FOR_PREDICTION,
        PREDICTION_CLOSED,
        RESULT_AVAILABLE
    }
}