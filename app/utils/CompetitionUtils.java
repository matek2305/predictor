package utils;

import domain.dto.web.CompetitionTableEntry;
import domain.entity.Competition;
import domain.entity.PredictorPoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Competition utils.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public final class CompetitionUtils {

    public static final List<CompetitionTableEntry> buildTable(Set<PredictorPoints> predictorPoints) {
        List<PredictorPoints> sortedPoints = predictorPoints.stream().sorted((pp1, pp2) -> Integer.compare(pp1.points, pp2.points) * -1).collect(Collectors.toList());
        List<CompetitionTableEntry> table = new ArrayList<>();

        for (int i = 0; i < sortedPoints.size(); i++) {
            CompetitionTableEntry entry = new CompetitionTableEntry(i + 1, sortedPoints.get(i));
            table.add(entry);
        }

        return table;
    }

    public static final int calculatePosition(Competition competition, Long predictorId) {
        int predictorPoints = competition.predictorsPoints.stream().filter(pp -> predictorId.equals(pp.predictor.id)).map(pp -> pp.points).findFirst().get();
        List<Integer> table = competition.predictorsPoints.stream().sorted((p1, p2) -> Integer.compare(p1.points, p2.points) * -1)
                .map(pp -> pp.points).distinct().collect(Collectors.toList());

        return table.indexOf(predictorPoints) + 1;
    }

    private CompetitionUtils() {

    }
}
