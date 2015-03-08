package domain.dto.web;

import domain.entity.Competition;

/**
 * Created by Hatake on 2015-03-07.
 */
public class CompetitionData {

    public Long id;
    public String name;
    public String description;
    public String admin;

    public CompetitionData(Competition competition) {
        this.id = competition.id;
        this.name = competition.name;
        this.description = competition.description;
        this.admin = competition.admin.login;
    }
}
