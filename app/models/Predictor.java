package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @NotNull
    public String password;

    /**
     * User registration date (with time).
     */
    @JsonIgnore
    @Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date registrationDate;

    /**
     * User authentication token.
     */
    @JsonIgnore
    @Column(name = "auth_token")
    public String authenticationToken;

    /**
     * Authentication token expiration date.
     */
    @JsonIgnore
    @Column(name = "token_exp_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date tokenExpirationDate;

    /**
     * Predictor point in all competitions that he participating in.
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "predictor")
    public List<PredictorPoints> points;
}
