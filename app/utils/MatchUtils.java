package utils;

import domain.entity.Match;
import domain.entity.Prediction;
import domain.dto.MatchDetails;
import org.apache.commons.lang3.StringUtils;
import utils.settings.PredictorSettings;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public final class MatchUtils {

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

    public static int calculatePointsForPrediction(Match match, Prediction prediction) {
        if (match.homeTeamScore == prediction.homeTeamScore && match.awayTeamScore == prediction.awayTeamScore) {
            return PredictorSettings.getInt(PredictorSettings.Setting.PREDICTION_FULL_POINTS);
        }

        if ((match.homeTeamScore - match.awayTeamScore < 0) && (prediction.homeTeamScore - prediction.awayTeamScore < 0)) {
            return PredictorSettings.getInt(PredictorSettings.Setting.PREDICTION_WINNER_POINTS);
        }

        if ((match.homeTeamScore - match.awayTeamScore > 0) && (prediction.homeTeamScore - prediction.awayTeamScore > 0)) {
            return PredictorSettings.getInt(PredictorSettings.Setting.PREDICTION_WINNER_POINTS);
        }

        if ((match.homeTeamScore - match.awayTeamScore == 0) && (prediction.homeTeamScore - prediction.awayTeamScore == 0)) {
            return PredictorSettings.getInt(PredictorSettings.Setting.PREDICTION_DRAW_POINTS);
        }

        return PredictorSettings.getInt(PredictorSettings.Setting.PREDICTION_MISSED_POINTS);
    }

    public static Date calculatePredictionLockTime(Date matchStartDate) {
        LocalDateTime predictionLockTime = calculatePredictionLockTime(DateHelper.toLocalDateTime(matchStartDate));
        return DateHelper.toDate(predictionLockTime);
    }

    public static LocalDateTime calculatePredictionLockTime(LocalDateTime matchStartDate) {
        int blockTimeInMinutes = PredictorSettings.getInt(PredictorSettings.Setting.PREDICTION_BLOCK_TIME);
        return matchStartDate.minusMinutes(blockTimeInMinutes);
    }

    private MatchUtils() {

    }
}
