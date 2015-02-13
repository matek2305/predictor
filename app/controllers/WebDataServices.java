package controllers;

import models.Competition;
import models.CompetitionRepository;
import models.Match;
import models.MatchRepository;
import models.dto.web.HomePageData;
import play.mvc.Result;
import utils.BusinessLogic;

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

    @Inject
    private MatchRepository matchRepository;

    @Inject
    private CompetitionRepository competitionRepository;

    @BusinessLogic
    public Result homePage() {
        List<Match> predictions = matchRepository.findByStatusAndPredictorOrderByStartDateAsc(Match.Status.OPEN_FOR_PREDICTION, getCurrentUser().id);
        List<Match> matches = matchRepository.findByStatusAndAdminOrderByStartDateAsc(Match.Status.PREDICTION_CLOSED, getCurrentUser().id);
        List<Competition> competitions = competitionRepository.findForPredictor(getCurrentUser().id);
        return ok(new HomePageData(getCurrentUser().id, predictions, matches, competitions));
    }
}
