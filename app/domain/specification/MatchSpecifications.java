package domain.specification;

import domain.entity.*;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Hatake on 2015-02-21.
 */
public final class MatchSpecifications {

    public static Specification<Match> hasStatus(Match.Status status) {
        return (root, query, cb) -> cb.equal(root.get(Match_.status), status);
    }

    public static Specification<Match> hasAdminWithId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Match_.competition).get(Competition_.admin).get(Predictor_.id), id);
    }

    public static Specification<Match> hasPredictorWithId(Long id) {
        return (root, query, cb) -> cb.equal(root.join(Match_.competition).join(Competition_.predictorsPoints).get(PredictorPoints_.id), id);
    }

    public static Specification<Match> isFromCompetitionWithId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Match_.competition).get(Competition_.id), id);
    }

    private MatchSpecifications() {

    }
}
