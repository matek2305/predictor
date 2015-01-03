package controllers.actors;

import akka.actor.UntypedActor;
import models.Match;
import models.MatchRepository;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import utils.DateHelper;
import utils.PredictorSettings;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class MatchScannerServiceActor extends UntypedActor {

    private MatchRepository matchRepository;

    public MatchScannerServiceActor(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        JPA.withTransaction(() -> findAndBlockPredictionsForMatches());
    }

    private void findAndBlockPredictionsForMatches() {
        int blockTimeInMinutes = Play.application().configuration().getInt(PredictorSettings.PREDICTION_BLOCK_TIME);
        LocalDateTime predictionTimeLimit = LocalDateTime.now().plusMinutes(blockTimeInMinutes);
        List<Match> matches = matchRepository.findByStatusAndStartDateLessThan(Match.Status.OPEN_FOR_PREDICTION, DateHelper.toDate(predictionTimeLimit));

        Logger.debug("Found " + matches.size() + " matches to block predictions");

        matches.forEach(m -> m.status = Match.Status.PREDICTION_CLOSED);
        matchRepository.save(matches);
    }
}