package models.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.Match;

/**
 * Created by Hatake on 2015-02-10.
 */
public class MatchData extends Match {

    @JsonProperty("comeptition")
    public Long competitionId;

    public MatchData(Match match) {
        this.id = match.id;
        this.homeTeamName = match.homeTeamName;
        this.awayTeamName = match.awayTeamName;
        this.homeTeamScore = match.homeTeamScore;
        this.awayTeamScore = match.awayTeamScore;
        this.startDate = match.startDate;
        this.comments = match.comments;
        this.competitionId = match.competition.id;
    }
}
