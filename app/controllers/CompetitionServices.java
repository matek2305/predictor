package controllers;

import api.response.ListResponse;
import controllers.validation.CreateCompetitionValidator;
import controllers.validation.JoinCompetitionValidator;
import domain.dto.CompetitionDetails;
import domain.dto.JoinCompetitionRequest;
import domain.dto.MatchDetails;
import domain.dto.web.CompetitionData;
import domain.dto.web.CompetitionDataForAdmin;
import domain.dto.web.CompetitionListElement;
import domain.entity.Competition;
import domain.entity.Match;
import domain.entity.PredictorPoints;
import domain.repository.CompetitionRepository;
import domain.repository.PredictorPointsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import play.mvc.Result;
import utils.BusinessLogic;
import utils.PredictorSecurity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static domain.specification.CompetitionSpecifications.hasAdminWithId;
import static domain.specification.CompetitionSpecifications.hasPredictorWithId;
import static org.springframework.data.jpa.domain.Specifications.not;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Competition services controller.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
@Singleton
public class CompetitionServices extends CommonPredictorService {

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private PredictorPointsRepository predictorPointsRepository;

    @BusinessLogic
    public Result getCompetitionData(Long id) {
        Competition competition = competitionRepository.findOne(id);
        if (competition == null) {
            return notFound();
        }

        if (getCurrentUser().id.equals(competition.admin.id)) {
            return ok(new CompetitionDataForAdmin(competition));
        }

        return ok(new CompetitionData(competition));
    }

    @BusinessLogic
    public Result getCompetitionList() {
        Specifications<Competition> competitionSpecification = where(hasPredictorWithId(getCurrentUser().id));
        if (getBoolFromQueryString("createdByMe")) {
            competitionSpecification = competitionSpecification.and(hasAdminWithId(getCurrentUser().id));
        }

        if (getBoolFromQueryString("imNotAdmin")) {
            competitionSpecification = competitionSpecification.and(not(hasAdminWithId(getCurrentUser().id)));
        }

        Sort nameAsc = new Sort(Sort.Direction.ASC, "name");
        Page<Competition> page = competitionRepository.findAll(competitionSpecification, getPageRequest(nameAsc));
        List<CompetitionListElement> data = page.getContent().stream().map(c -> new CompetitionListElement(getCurrentUser().id, c))
                .collect(Collectors.toList());

        return ok(new ListResponse<>(data, page.getTotalElements(), page.getTotalPages()));
    }

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
                competition.matches = new HashSet<>();
            }

            competition.matches.add(match);
        }

        competition = competitionRepository.save(competition);

        PredictorPoints predictorPoints = new PredictorPoints();
        predictorPoints.competition = competition;
        predictorPoints.predictor = getCurrentUser();
        predictorPointsRepository.save(predictorPoints);

        return created(new CompetitionDataForAdmin(competitionRepository.findOne(competition.id)));
    }
}
