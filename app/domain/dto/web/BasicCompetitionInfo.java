package domain.dto.web;

import domain.entity.Competition;

/**
 * Created by Hatake on 2015-03-09.
 */
public class BasicCompetitionInfo extends AbstractEntityData {

    public String name;

    public BasicCompetitionInfo(Competition competition) {
        super(competition.id);

        this.name = competition.name;
    }
}
