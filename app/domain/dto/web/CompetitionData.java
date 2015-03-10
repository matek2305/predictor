package domain.dto.web;

import domain.entity.Competition;
import utils.CompetitionUtils;

import java.util.List;

/**
 * Created by Hatake on 2015-03-07.
 */
public class CompetitionData extends BasicCompetitionInfo {

    public String description;
    public String admin;
    public String leader;
    public int numberOfPredictors;
    public int numberOfMatches;
    public int totalPoints;
    public List<CompetitionTableEntry> table;

    public CompetitionData(Competition competition) {
        super(competition);

        this.description = competition.description;
        this.admin = competition.admin.login;
        this.numberOfPredictors = competition.predictorsPoints.size();
        this.numberOfMatches = competition.matches.size();
        this.totalPoints = competition.predictorsPoints.stream().mapToInt(pp -> pp.points).sum();
        this.table = CompetitionUtils.buildTable(competition.predictorsPoints);
        this.leader = this.table.stream().filter(e -> e.position == 1).findFirst().get().predictor;
    }
}
