package controllers;

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

    @BusinessLogic
    public Result homePage() {
        List<Match> matches = matchRepository.findByStatusAndPredictorOrderByStartDateAsc(Match.Status.OPEN_FOR_PREDICTION, getCurrentUser().id);
        return ok(new HomePageData(matches));
    }
}
