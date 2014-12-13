package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
public class Competition extends AbstractPredictorEntity {

    /**
     * Name of the competition.
     */
    @NotNull
    public String name;

    /**
     * Description.
     */
    public String description;

    /**
     * Competition admin.
     * Predictor who created competition.
     */
    @ManyToOne
    public Predictor admin;

    /**
     * Lists of matches in this competition.
     */
    @OneToMany
    public List<Match> matches;

    /**
     * Points for all predictors that are participating in this competition.
     */
    @OneToMany
    public List<PredictorPoints> predictorsPoints;
}
