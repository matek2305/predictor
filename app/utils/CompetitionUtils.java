package utils;

import models.Competition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Competition utils.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public final class CompetitionUtils {

    public static final int calculatePosition(Competition competition, Long predictorId) {
        int predictorPoints = competition.predictorsPoints.stream().filter(pp -> predictorId.equals(pp.predictor.id)).map(pp -> pp.points).findFirst().get();
        List<Integer> table = competition.predictorsPoints.stream().sorted((p1, p2) -> Integer.compare(p1.points, p2.points) * -1)
                .map(pp -> pp.points).distinct().collect(Collectors.toList());

        return table.indexOf(predictorPoints) + 1;
    }

    private CompetitionUtils() {

    }
}
