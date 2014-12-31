package utils;

import models.dto.MatchDetails;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public final class MatchUtils {

    private static final String MATCH_LABEL_FORMAT = "%s vs %s";

    public static String getMatchLabel(MatchDetails matchDetails) {
        return getMatchLabel(matchDetails.homeTeamName, matchDetails.awayTeamName);
    }

    public static String getMatchLabel(String homeTeamName, String awayTeamName) {
        return String.format(MATCH_LABEL_FORMAT, homeTeamName, awayTeamName);
    }

    public static boolean equals(MatchDetails a, MatchDetails b) {
        if (!StringUtils.equals(a.homeTeamName, b.homeTeamName)) {
            return false;
        }

        if (!StringUtils.equals(a.awayTeamName, b.awayTeamName)) {
            return false;
        }

        if (a.startDate.getTime() != b.startDate.getTime()) {
            return false;
        }

        return true;
    }

    private MatchUtils() {

    }
}
