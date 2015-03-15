package controllers;

import api.response.ListResponse;
import controllers.validation.CancelMatchRequestValidation;
import controllers.validation.ExtendedMatchDetailsValidation;
import controllers.validation.MatchResultDataValidation;
import controllers.validation.ValidationContext;
import domain.dto.CancelMatchRequest;
import domain.dto.ExtendedMatchDetails;
import domain.dto.MatchResultData;
import domain.dto.web.MatchListElement;
import domain.entity.Match;
import domain.entity.Prediction;
import domain.entity.PredictorPoints;
import domain.repository.CompetitionRepository;
import domain.repository.MatchRepository;
import domain.repository.PredictorPointsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import play.mvc.Result;
import utils.BusinessLogic;
import utils.MatchUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static domain.specification.MatchSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Match services controller.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class MatchServices extends CommonPredictorService {

    private static final int CANCELLED_MATCH_POINTS = 0;
    private static final int CANCELLED_MATCH_TEAM_SCORE = -1;

    @Inject
    private MatchRepository matchRepository;

    @Inject
    private PredictorPointsRepository predictorPointsRepository;

    @Inject
    private CompetitionRepository competitionRepository;

    @BusinessLogic
    public Result getMatchList() {
        Specifications<Match> matchSpecification = where(hasPredictorWithId(getCurrentUser().id));
        if (getBoolFromQueryString("admin")) {
            matchSpecification = where(hasAdminWithId(getCurrentUser().id));
        }

        EnumSet<Match.Status> statusSet = getEnumSetFromQueryString("status", Match.Status.class);
        if (!statusSet.isEmpty()) {
            matchSpecification = matchSpecification.and(hasStatusIn(statusSet));
        }

        Long competitionId = getLongFromQueryString("competition");
        if (competitionId != null) {
            matchSpecification = matchSpecification.and(isFromCompetitionWithId(competitionId));
        }

        Sort startDateAsc = new Sort(Sort.Direction.ASC, "startDate");
        Page<Match> page = matchRepository.findAll(matchSpecification, getPageRequest(startDateAsc));
        List<MatchListElement> data = page.getContent().stream().map(m -> {
            MatchListElement matchData = new MatchListElement(m);
            if (getBoolFromQueryString("predictions")) {
                m.predictions.stream().filter(p -> p.predictor.id.equals(getCurrentUser().id)).findFirst().ifPresent(p -> {
                    matchData.homeTeamScore = p.homeTeamScore;
                    matchData.awayTeamScore = p.awayTeamScore;
                });
            }
            return matchData;
        }).collect(Collectors.toList());

        return ok(new ListResponse<>(data, page.getTotalElements(), page.getTotalPages()));
    }

    @BusinessLogic(validator = MatchResultDataValidation.class)
    public Result result() {
        MatchResultData data = prepareRequest(MatchResultData.class);
        Match match = matchRepository.findOne(data.matchId);
        match.homeTeamScore = data.homeTeamScore;
        match.awayTeamScore = data.awayTeamScore;
        match.status = Match.Status.RESULT_AVAILABLE;

        for (Prediction prediction : match.predictions) {
            prediction.points = MatchUtils.calculatePointsForPrediction(match, prediction);

            PredictorPoints predictorPoints = predictorPointsRepository.findByCompetitionAndPredictor(match.competition.id, prediction.predictor.id);
            predictorPoints.points += prediction.points;
            predictorPointsRepository.save(predictorPoints);
        }

        return ok(matchRepository.save(match));
    }

    @BusinessLogic(validator = MatchResultDataValidation.class, validationContext = ValidationContext.MATCH_RESULT_CHANGE)
    public Result changeResult() {
        MatchResultData data = prepareRequest(MatchResultData.class);
        Match match = matchRepository.findOne(data.matchId);
        match.homeTeamScore = data.homeTeamScore;
        match.awayTeamScore = data.awayTeamScore;

        for (Prediction prediction : match.predictions) {
            int subtract = prediction.points;
            prediction.points = MatchUtils.calculatePointsForPrediction(match, prediction);

            PredictorPoints predictorPoints = predictorPointsRepository.findByCompetitionAndPredictor(match.competition.id, prediction.predictor.id);
            predictorPoints.points -= subtract;
            predictorPoints.points += prediction.points;
            predictorPointsRepository.save(predictorPoints);
        }

        return ok(matchRepository.save(match));
    }

    @BusinessLogic(validator = CancelMatchRequestValidation.class)
    public Result cancel() {
        CancelMatchRequest request = prepareRequest(CancelMatchRequest.class);
        Match match = matchRepository.findOne(request.matchId);

        if (match.status == Match.Status.RESULT_AVAILABLE) {
            for (Prediction prediction : match.predictions) {
                int subtract = prediction.points;
                prediction.points = CANCELLED_MATCH_POINTS;

                PredictorPoints predictorPoints = predictorPointsRepository.findByCompetitionAndPredictor(match.competition.id, prediction.predictor.id);
                predictorPoints.points -= subtract;
                predictorPointsRepository.save(predictorPoints);
            }
        }

        match.homeTeamScore = CANCELLED_MATCH_TEAM_SCORE;
        match.awayTeamScore = CANCELLED_MATCH_TEAM_SCORE;
        match.status = Match.Status.CANCELLED;
        match.comments = request.comments;
        return ok(matchRepository.save(match));
    }

    @BusinessLogic(validator = ExtendedMatchDetailsValidation.class)
    public Result create() {
        ExtendedMatchDetails details = prepareRequest(ExtendedMatchDetails.class);
        Match match = new Match(details);
        match.status = Match.Status.OPEN_FOR_PREDICTION;
        match.competition = competitionRepository.findOne(details.competitionId);;
        return created(matchRepository.save(match));
    }
}
