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
    public List<CompetitionTableEntry> table;

    public CompetitionData(Competition competition) {
        super(competition);

        this.description = competition.description;
        this.admin = competition.admin.login;
        this.table = CompetitionUtils.buildTable(competition.predictorsPoints);
    }
}
