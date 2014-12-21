package models;

import utils.dev.InitialData;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
@Table(name = "predictor")
@InitialData(name = "users", order = 1)
public class Predictor extends AbstractPredictorEntity {

    /**
     * User login.
     */
    @Column(nullable = false, unique = true)
    public String login;

    /**
     * User password hash (MD5).
     */
    @NotNull
    public String password;

    /**
     * User registration date (with time).
     */
    @Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date registrationDate;

    /**
     * Predictor point in all competitions that he participating in.
     */
    @OneToMany
    public List<PredictorPoints> points;
}
