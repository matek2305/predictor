package domain.dto.web;

import domain.entity.PredictorPoints;

/**
 * Created by Hatake on 2015-03-08.
 */
public class CompetitionTableEntry {

    public int position;
    public String predictor;
    public int points;

    public CompetitionTableEntry(int position, PredictorPoints predictorPoints) {
        this(position, predictorPoints.predictor.login, predictorPoints.points);
    }

    public CompetitionTableEntry(int position, String predictor, int points) {
        this.position = position;
        this.predictor = predictor;
        this.points = points;
    }
}
