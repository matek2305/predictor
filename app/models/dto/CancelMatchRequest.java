package models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class CancelMatchRequest {

    @JsonProperty("match")
    public Long matchId;
    public String comments;
}
