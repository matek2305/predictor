package models.dto.web;

import models.Competition;

/**
 * Created by Hatake on 2015-02-10.
 */
public class CompetitionData {

    public Long id;
    public String name;
    public String description;

    public CompetitionData(Competition competition) {
        this.id = competition.id;
        this.name = competition.name;
        this.description = competition.description;
    }
}
