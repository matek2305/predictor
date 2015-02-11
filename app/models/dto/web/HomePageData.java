package models.dto.web;

import models.Competition;
import models.Match;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Hatake on 2015-02-10.
 */
public class HomePageData {

    public Set<CompetitionData> competitions;
    public Set<MatchData> matches;

    public HomePageData(Long predictorId, List<Match> matches, List<Competition> competitions) {
        this.competitions = competitions.stream().map(c -> new CompetitionData(c)).collect(Collectors.toSet());
        this.matches = matches.stream().map(m -> {
            MatchData data = new MatchData(m);
            m.predictions.stream().filter(p -> p.predictor.id.equals(predictorId)).findFirst().ifPresent(p -> {
                data.homeTeamScore = p.homeTeamScore;
                data.awayTeamScore = p.awayTeamScore;
            });
            return data;
        }).collect(Collectors.toSet());
    }
}
