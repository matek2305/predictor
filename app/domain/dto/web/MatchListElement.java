package domain.dto.web;

import domain.entity.Match;
import utils.MatchUtils;

import java.util.Date;

/**
 * Created by Hatake on 2015-02-10.
 */
public class MatchListElement extends AbstractEntityData {

    public String homeTeamName;
    public String awayTeamName;
    public Integer homeTeamScore;
    public Integer awayTeamScore;
    public Date startDate;
    public Date predictionLockDate;
    public Match.Status status;
    public String comments;
    public BasicCompetitionInfo competition;

    public MatchListElement(Match match) {
        super(match.id);

        this.homeTeamName = match.homeTeamName;
        this.awayTeamName = match.awayTeamName;
        this.homeTeamScore = match.homeTeamScore >= 0 ? match.homeTeamScore : null;
        this.awayTeamScore = match.awayTeamScore >= 0 ? match.awayTeamScore : null;
        this.startDate = match.startDate;
        this.predictionLockDate = MatchUtils.calculatePredictionLockTime(match.startDate);
        this.comments = match.comments;
        this.status = match.status;
        this.competition = new BasicCompetitionInfo(match.competition);
    }
}
