package domain.dto.web;

import domain.entity.Competition;

/**
 * Created by Hatake on 2015-02-18.
 */
public class ExtendCompetitionData extends CompetitionData {

    public int numberOfPredictors;

    public ExtendCompetitionData(Long predictorId, Competition competition) {
        super(predictorId, competition);
        this.numberOfPredictors = competition.predictorsPoints.size();
    }
}
