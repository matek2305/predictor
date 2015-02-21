package domain.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.entity.Match;
import utils.MatchUtils;

import java.util.Date;

/**
 * Created by Hatake on 2015-02-10.
 */
public class MatchData {

    public Long id;
    public String homeTeamName;
    public String awayTeamName;
    public Integer homeTeamScore;
    public Integer awayTeamScore;
    public Date startDate;
    public Date predictionLockDate;
    public Match.Status status;
    public String comments;

    @JsonProperty("comeptition")
    public Long competitionId;

    public MatchData(Match match) {
        this.id = match.id;
        this.homeTeamName = match.homeTeamName;
        this.awayTeamName = match.awayTeamName;
        this.homeTeamScore = match.homeTeamScore >= 0 ? match.homeTeamScore : null;
        this.awayTeamScore = match.awayTeamScore >= 0 ? match.awayTeamScore : null;
        this.startDate = match.startDate;
        this.predictionLockDate = MatchUtils.calculatePredictionLockTime(match.startDate);
        this.comments = match.comments;
        this.competitionId = match.competition.id;
        this.status = match.status;
    }
}
