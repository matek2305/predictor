package controllers;

import controllers.validation.CreateCompetitionValidator;
import controllers.validation.JoinCompetitionValidator;
import domain.dto.CompetitionDetails;
import domain.dto.CompetitionWithSecurityCode;
import domain.dto.JoinCompetitionRequest;
import domain.dto.MatchDetails;
import domain.entity.Competition;
import domain.entity.Match;
import domain.entity.PredictorPoints;
import domain.repository.CompetitionRepository;
import domain.repository.PredictorPointsRepository;
import play.mvc.Result;
import utils.BusinessLogic;
import utils.PredictorSecurity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;

/**
 * Competition services controller.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class CompetitionServices extends CommonPedictorService {

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private PredictorPointsRepository predictorPointsRepository;

    /**
     * Join competition service.
     * @return
     */
    @BusinessLogic(validator = JoinCompetitionValidator.class)
    public Result joinCompetition() {
        JoinCompetitionRequest request = prepareRequest(JoinCompetitionRequest.class);
        PredictorPoints predictorPoints = new PredictorPoints();
        predictorPoints.competition = competitionRepository.findOne(request.competitionId);
        predictorPoints.predictor = getCurrentUser();
        return created(predictorPointsRepository.save(predictorPoints));
    }

    /**
     * Create competition service.
     * @return
     */
    @BusinessLogic(validator = CreateCompetitionValidator.class)
    public Result createCompetition() {
        CompetitionDetails competitionDetails = prepareRequest(CompetitionDetails.class);
        Competition competition = new Competition(competitionDetails);
        competition.admin = getCurrentUser();
        competition.securityCode = PredictorSecurity.generateCompetitionCode();

        for (MatchDetails matchDetails : competitionDetails.matches) {
            Match match = new Match(matchDetails);
            match.status = Match.Status.OPEN_FOR_PREDICTION;
            match.competition = competition;

            if (competition.matches == null) {
                competition.matches = new ArrayList<>();
            }

            competition.matches.add(match);
        }

        competition = competitionRepository.save(competition);

        PredictorPoints predictorPoints = new PredictorPoints();
        predictorPoints.competition = competition;
        predictorPoints.predictor = getCurrentUser();
        predictorPointsRepository.save(predictorPoints);

        return created(new CompetitionWithSecurityCode(competition));
    }
}
