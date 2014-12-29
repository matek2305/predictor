package models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class PredictionDetails {

    @JsonProperty("match")
    private Long matchId;
    private int homeTeamScore;
    private int awayTeamScore;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }
}
