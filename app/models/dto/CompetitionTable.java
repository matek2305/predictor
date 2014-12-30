package models.dto;

import java.util.List;

/**
 * Created by Hatake on 2014-12-27.
 */
public class CompetitionTable {

    public String competition;
    public List<CompetitionTableRow> content;

    public CompetitionTable(String competition, List<CompetitionTableRow> content) {
        this.competition = competition;
        this.content = content;
    }
}
