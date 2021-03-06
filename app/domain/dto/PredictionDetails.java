package domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class PredictionDetails {

    @JsonProperty("match")
    public Long matchId;
    public int homeTeamScore;
    public int awayTeamScore;
}
