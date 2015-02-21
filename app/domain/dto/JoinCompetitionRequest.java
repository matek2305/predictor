package domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class JoinCompetitionRequest {

    @JsonProperty("competition")
    public Long competitionId;

    @JsonProperty("code")
    public String competitionCode;
}
