package models.dto;

import java.util.List;

/**
 * Created by Hatake on 2014-12-27.
 */
public class CompetitionTable {

    private String competition;
    private List<CompetitionTableRow> content;

    public CompetitionTable(String competition, List<CompetitionTableRow> content) {
        this.competition = competition;
        this.content = content;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public List<CompetitionTableRow> getContent() {
        return content;
    }

    public void setContent(List<CompetitionTableRow> content) {
        this.content = content;
    }
}
