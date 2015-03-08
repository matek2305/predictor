package domain.dto.web;

import domain.entity.Competition;

/**
 * Created by Hatake on 2015-03-07.
 */
public class CompetitionDataForAdmin extends CompetitionData {

    public String securityCode;

    public CompetitionDataForAdmin(Competition competition) {
        super(competition);

        this.securityCode = competition.securityCode;
    }
}
