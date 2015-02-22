package domain.specification;

import domain.entity.Competition;
import domain.entity.Competition_;
import domain.entity.PredictorPoints_;
import domain.entity.Predictor_;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Hatake on 2015-02-21.
 */
public final class CompetitionSpecifications {

    public static Specification<Competition> hasAdminWithId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Competition_.admin).get(Predictor_.id), id);
    }

    public static Specification<Competition> hasPredictorWithId(Long id) {
        return (root, query, cb) -> cb.equal(root.join(Competition_.predictorsPoints).get(PredictorPoints_.predictor).get(Predictor_.id), id);
    }

    private CompetitionSpecifications() {

    }
}
