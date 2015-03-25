package domain.dto.web;

import domain.entity.Competition;
import utils.CompetitionUtils;

/**
 * Created by Hatake on 2015-02-10.
 */
public class CompetitionListElement extends BasicCompetitionInfo {

    public int position;
    public int points;
    public int numberOfPredictors;

    public CompetitionListElement(Long predictorId, Competition competition) {
        super(competition);

        this.position = CompetitionUtils.calculatePosition(competition, predictorId);
        this.points = competition.predictorsPoints.stream().filter(p -> p.predictor.id.equals(predictorId)).findFirst().get().points;
        this.numberOfPredictors = competition.predictorsPoints.size();
    }
}
