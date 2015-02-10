package models.dto.web;

import models.Match;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Hatake on 2015-02-10.
 */
public class HomePageData {

    public Set<MatchData> matches;
    public Set<CompetitionData> competitions;

    public HomePageData(List<Match> matches) {
        this.matches = matches.stream().map(m -> new MatchData(m)).collect(Collectors.toSet());
        this.competitions = matches.stream().map(m -> m.competition).distinct().map(c -> new CompetitionData(c)).collect(Collectors.toSet());
    }
}
