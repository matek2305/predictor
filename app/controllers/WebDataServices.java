package controllers;

import models.Competition;
import models.CompetitionRepository;
import models.Match;
import models.MatchRepository;
import models.dto.web.HomePageData;
import org.springframework.data.domain.PageRequest;
import play.mvc.Result;
import utils.BusinessLogic;
import utils.LimitResults;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 * Created by Hatake on 2015-02-10.
 */
@Named
@Singleton
public class WebDataServices extends PredictorServicesController {

    private static final LimitResults TOP_FIVE_RESULTS = new LimitResults(5);
    private static final LimitResults TOP_TEN_RESULTS = new LimitResults(10);

    @Inject
    private MatchRepository matchRepository;

    @Inject
    private CompetitionRepository competitionRepository;

    @BusinessLogic
    public Result homePage() {
        List<Match> predictions = matchRepository.findByStatusAndPredictorOrderByStartDateAsc(Match.Status.OPEN_FOR_PREDICTION, getCurrentUser().id, TOP_FIVE_RESULTS);
        List<Match> matches = matchRepository.findByStatusAndAdminOrderByStartDateAsc(Match.Status.PREDICTION_CLOSED, getCurrentUser().id, TOP_FIVE_RESULTS);
        List<Competition> competitions = competitionRepository.findForPredictorOrderByPointsDesc(getCurrentUser().id, TOP_TEN_RESULTS);
        return ok(new HomePageData(getCurrentUser().id, predictions, matches, competitions));
    }
}
