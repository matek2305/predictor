package utils;

import domain.entity.Match;
import domain.entity.Prediction;
import org.junit.Before;
import org.junit.Test;
import utils.settings.PredictorSettings;
import utils.settings.SettingsProvider;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link utils.MatchUtils} class.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class MatchUtilsTest {

    @Before
    public void setUp() {
        SettingsProvider settingsProvider = mock(SettingsProvider.class);
        when(settingsProvider.getInt(eq(PredictorSettings.Setting.PREDICTION_FULL_POINTS))).thenReturn(7);
        when(settingsProvider.getInt(eq(PredictorSettings.Setting.PREDICTION_DRAW_POINTS))).thenReturn(5);
        when(settingsProvider.getInt(eq(PredictorSettings.Setting.PREDICTION_WINNER_POINTS))).thenReturn(3);
        when(settingsProvider.getInt(eq(PredictorSettings.Setting.PREDICTION_MISSED_POINTS))).thenReturn(0);

        PredictorSettings.setSettingsProvider(settingsProvider);
    }

    @Test
     public void testCalculatePointsHomeTeamWin() {
        Match homeTeamWin = createMatch(3, 1);

        assertEquals(7, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(3, 1)));
        assertEquals(3, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(2, 1)));
        assertEquals(0, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(3, 3)));
        assertEquals(0, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(0, 1)));
    }

    @Test
    public void testCalculatePointsAwayTeamWin() {
        Match homeTeamWin = createMatch(0, 1);

        assertEquals(0, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(3, 1)));
        assertEquals(3, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(2, 3)));
        assertEquals(0, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(3, 3)));
        assertEquals(7, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(0, 1)));
    }

    @Test
    public void testCalculatePointsDraw() {
        Match homeTeamWin = createMatch(2, 2);

        assertEquals(7, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(2, 2)));
        assertEquals(5, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(0, 0)));
        assertEquals(0, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(2, 1)));
        assertEquals(0, MatchUtils.calculatePointsForPrediction(homeTeamWin, createPrediction(0, 1)));
    }

    private Match createMatch(int homeTeamScore, int awayTeamScore) {
        Match match = new Match();
        match.homeTeamScore = homeTeamScore;
        match.awayTeamScore = awayTeamScore;
        return match;
    }

    private Prediction createPrediction(int homeTeamScore, int awayTeamScore) {
        Prediction prediction = new Prediction();
        prediction.homeTeamScore = homeTeamScore;
        prediction.awayTeamScore = awayTeamScore;
        return prediction;
    }
}