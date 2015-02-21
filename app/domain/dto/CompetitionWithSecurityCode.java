package domain.dto;

import domain.entity.Competition;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class CompetitionWithSecurityCode extends Competition {

    public String securityCode;

    public CompetitionWithSecurityCode(Competition competition) {
        id = competition.id;
        name = competition.name;
        description = competition.description;
        securityCode = competition.securityCode;
        matches = competition.matches;
    }
}
