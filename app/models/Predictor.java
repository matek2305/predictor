package models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Hatake on 2014-12-13.
 */
@Entity
public class Predictor extends AbstractPredictorEntity {

    /**
     * User login.
     */
    @NotNull
    public String login;

    /**
     * User password hash (MD5).
     */
    @NotNull
    public String password;

    /**
     * User registration date (with time).
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    public Date registerDate;

    /**
     * Predictor point in all competitions that he participating in.
     */
    @OneToMany
    public List<PredictorPoints> points;
}
