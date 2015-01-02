package models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class ExtendedMatchDetails extends MatchDetails {

    @JsonProperty("competition")
    public Long competitionId;
}
