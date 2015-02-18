package models.dto.web;

import models.Competition;
import utils.CompetitionUtils;

/**
 * Created by Hatake on 2015-02-10.
 */
public class CompetitionData {

    public Long id;
    public String name;
    public int position;
    public int points;

    public CompetitionData(Long predictorId, Competition competition) {
        this.id = competition.id;
        this.name = competition.name;
        this.position = CompetitionUtils.calculatePosition(competition, predictorId);
        this.points = competition.predictorsPoints.stream().filter(p -> p.predictor.id.equals(predictorId)).findFirst().get().points;
    }
}
