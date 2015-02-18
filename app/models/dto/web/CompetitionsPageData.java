package models.dto.web;

import models.Competition;
import models.Match;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Hatake on 2015-02-10.
 */
public class CompetitionsPageData {

    public Set<ExtendCompetitionData> userCompetitions;
    public Set<ExtendCompetitionData> otherCompetitions;
    public Set<CompetitionData> competitions;

    public CompetitionsPageData(Long predictorId, List<Competition> userCompetitions, List<Competition> otherCompetitions, List<Competition> competitions) {
        this.userCompetitions = userCompetitions.stream().map(c -> new ExtendCompetitionData(predictorId, c)).collect(Collectors.toSet());
        this.otherCompetitions = otherCompetitions.stream().map(c -> new ExtendCompetitionData(predictorId, c)).collect(Collectors.toSet());
        this.competitions = competitions.stream().map(c -> new CompetitionData(predictorId, c)).collect(Collectors.toSet());
    }
}
