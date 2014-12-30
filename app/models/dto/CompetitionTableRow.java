package models.dto;

/**
 * Created by Hatake on 2014-12-27.
 */
public class CompetitionTableRow {

    public String user;
    public int points;

    public CompetitionTableRow(String user, int points) {
        this.user = user;
        this.points = points;
    }
}
