package domain.dto.web;

import domain.entity.Competition;
import domain.entity.Match;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Hatake on 2015-02-10.
 */
public class HomePageData {

    public Set<CompetitionData> competitions;
    public Set<MatchData> predictions;
    public Set<MatchData> matches;

    public HomePageData(Long predictorId, List<Match> predictions, List<Match> matches, List<Competition> competitions) {
        this.competitions = competitions.stream().map(c -> new CompetitionData(predictorId, c)).collect(Collectors.toSet());
        this.predictions = predictions.stream().map(m -> {
            MatchData data = new MatchData(m);
            m.predictions.stream().filter(p -> p.predictor.id.equals(predictorId)).findFirst().ifPresent(p -> {
                data.homeTeamScore = p.homeTeamScore;
                data.awayTeamScore = p.awayTeamScore;
            });
            return data;
        }).collect(Collectors.toSet());
        this.matches = matches.stream().map(MatchData::new).collect(Collectors.toSet());
    }
}
