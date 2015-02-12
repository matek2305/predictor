package utils;

import models.Match;
import models.Prediction;
import models.dto.MatchDetails;
import org.apache.commons.lang3.StringUtils;
import play.Play;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public final class MatchUtils {

    private static final String MATCH_LABEL_FORMAT = "%s vs %s";

    private static final int EXACT_PREDICTION_POINTS = 5;
    private static final int WINNER_PREDICTION_POINTS = 3;
    private static final int MISSED_PREDICTION_POINTS = 0;

    public static String getMatchLabel(MatchDetails matchDetails) {
        return getMatchLabel(matchDetails.homeTeamName, matchDetails.awayTeamName);
    }

    public static String getMatchLabel(Match match) {
        return getMatchLabel(match.homeTeamName, match.awayTeamName);
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

    public static boolean equals(Match a, MatchDetails b) {
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

    public static int calculatePoints(Match match, Prediction prediction) {
        if (match.homeTeamScore == prediction.homeTeamScore && match.awayTeamScore == prediction.awayTeamScore) {
            return EXACT_PREDICTION_POINTS;
        }

        if ((match.homeTeamScore - match.awayTeamScore < 0) && (prediction.homeTeamScore - prediction.awayTeamScore < 0)) {
            return WINNER_PREDICTION_POINTS;
        }

        if ((match.homeTeamScore - match.awayTeamScore > 0) && (prediction.homeTeamScore - prediction.awayTeamScore > 0)) {
            return WINNER_PREDICTION_POINTS;
        }

        if ((match.homeTeamScore - match.awayTeamScore == 0) && (prediction.homeTeamScore - prediction.awayTeamScore == 0)) {
            return WINNER_PREDICTION_POINTS;
        }

        return MISSED_PREDICTION_POINTS;
    }

    public static Date calculatePredictionLockTime(Date matchStartDate) {
        LocalDateTime predictionLockTime = calculatePredictionLockTime(DateHelper.toLocalDateTime(matchStartDate));
        return DateHelper.toDate(predictionLockTime);
    }

    public static LocalDateTime calculatePredictionLockTime(LocalDateTime matchStartDate) {
        int blockTimeInMinutes = Play.application().configuration().getInt(PredictorSettings.PREDICTION_BLOCK_TIME);
        return matchStartDate.minusMinutes(blockTimeInMinutes);
    }

    private MatchUtils() {

    }
}
