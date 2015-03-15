package domain.specification;

import domain.entity.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by Hatake on 2015-02-21.
 */
public final class MatchSpecifications {

    public static Specification<Match> hasStatusIn(EnumSet<Match.Status> statusList) {
        return (root, query, cb) -> root.get(Match_.status).in(statusList);
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
