package domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.dto.MatchDetails;
import utils.dev.InitialData;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
@Table(name = "match")
@InitialData(name = "matches", order = 3)
public class Match extends AbstractPredictorEntity {

    /**
     * Home team name.
     */
    @Column(name = "home_team_name", nullable = false)
    public String homeTeamName;

    /**
     * Away team team;
     */
    @Column(name = "away_team_name", nullable = false)
    public String awayTeamName;

    /**
     * Home team score (-1 if not available).
     */
    @Column(name = "home_team_score", columnDefinition = "integer default -1")
    public int homeTeamScore;

    /**
     * Away team score (-1 if not available).
     */
    @Column(name = "away_team_score", columnDefinition = "integer default -1")
    public int awayTeamScore;

    /**
     * Start date.
     */
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date startDate;

    /**
     * Current status.
     */
    @JsonIgnore
    @NotNull
    @Enumerated(EnumType.STRING)
    public Status status;

    /**
     * Comments (when cancelled).
     */
    public String comments;

    /**
     * Competition.
     */
    @JsonIgnore
    @NotNull
    @ManyToOne
    public Competition competition;

    /**
     * Lists of predictions for this match.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Prediction> predictions;

    public Match() {

    }

    public Match(MatchDetails details) {
        this.homeTeamName = details.homeTeamName;
        this.awayTeamName = details.awayTeamName;
        this.homeTeamScore = -1;
        this.awayTeamScore = -1;
        this.startDate = new Date(details.startDate.getTime());
    }

    /**
     * Match status.
     */
    public static enum Status {
        OPEN_FOR_PREDICTION,
        PREDICTION_CLOSED,
        RESULT_AVAILABLE,
        CANCELLED
    }
}