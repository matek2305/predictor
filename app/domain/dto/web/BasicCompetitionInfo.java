package domain.dto.web;

import domain.entity.Competition;

/**
 * Created by Hatake on 2015-03-09.
 */
public class BasicCompetitionInfo {

    public Long id;
    public String name;

    public BasicCompetitionInfo(Competition competition) {
        this(competition.id, competition.name);
    }

    public BasicCompetitionInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
