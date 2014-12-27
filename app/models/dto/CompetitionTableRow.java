package models.dto;

/**
 * Created by Hatake on 2014-12-27.
 */
public class CompetitionTableRow {

    private String user;
    private int points;

    public CompetitionTableRow(String user, int points) {
        this.user = user;
        this.points = points;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
