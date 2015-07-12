package utils;

import domain.entity.Competition;
import domain.entity.Predictor;
import domain.entity.PredictorPoints;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Tests for {@link utils.CompetitionUtils} class.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class CompetitionUtilsTest {

    @Test
    public void testCalculatePosition() {
        Competition competition = createCompetition();

        assertEquals(2, CompetitionUtils.calculatePosition(competition, 1L));
        assertEquals(4, CompetitionUtils.calculatePosition(competition, 2L));
        assertEquals(1, CompetitionUtils.calculatePosition(competition, 3L));
        assertEquals(2, CompetitionUtils.calculatePosition(competition, 4L));
        assertEquals(2, CompetitionUtils.calculatePosition(competition, 5L));
        assertEquals(5, CompetitionUtils.calculatePosition(competition, 6L));
        assertEquals(3, CompetitionUtils.calculatePosition(competition, 7L));
        assertEquals(2, CompetitionUtils.calculatePosition(competition, 8L));
        assertEquals(5, CompetitionUtils.calculatePosition(competition, 9L));
    }

    private Competition createCompetition() {
        Competition competition = new Competition();
        competition.predictorsPoints = new HashSet<>();
        competition.predictorsPoints.add(createPredictorPoints(1L, 23));
        competition.predictorsPoints.add(createPredictorPoints(2L, 3));
        competition.predictorsPoints.add(createPredictorPoints(3L, 26));
        competition.predictorsPoints.add(createPredictorPoints(4L, 23));
        competition.predictorsPoints.add(createPredictorPoints(5L, 23));
        competition.predictorsPoints.add(createPredictorPoints(6L, 0));
        competition.predictorsPoints.add(createPredictorPoints(7L, 11));
        competition.predictorsPoints.add(createPredictorPoints(8L, 23));
        competition.predictorsPoints.add(createPredictorPoints(9L, 0));
        return competition;
    }

    private PredictorPoints createPredictorPoints(Long predictorId, int points) {
        PredictorPoints predictorPoints = new PredictorPoints();
        predictorPoints.predictor = createPredictor(predictorId);
        predictorPoints.points = points;
        return predictorPoints;
    }

    private Predictor createPredictor(Long id) {
        Predictor predictor = new Predictor();
        predictor.id = id;
        return predictor;
    }
}