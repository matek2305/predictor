package domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.dto.CompetitionDetails;
import utils.dev.InitialData;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
@Table(name = "competition")
@InitialData(name = "competition", order = 2)
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

    @JsonIgnore
    @Column(name = "security_code", nullable = false)
    public String securityCode;

    /**
     * Competition admin.
     * Predictor who created competition.
     */
    @JsonIgnore
    @ManyToOne
    public Predictor admin;

    /**
     * Lists of matches in this competition.
     */
    @OneToMany(mappedBy = "competition", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Match> matches;

    /**
     * Points for all predictors that are participating in this competition.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "competition", fetch = FetchType.EAGER)
    public List<PredictorPoints> predictorsPoints;

    public Competition() {

    }

    public Competition(CompetitionDetails details) {
        this.name = details.name;
        this.description = details.description;
    }
}
