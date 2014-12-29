package models;

import models.dto.AuthenticationDetails;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

/**
 * Created by Hatake on 2014-12-13.
 */
@Named
@Singleton
public interface PredictorRepository extends PredictorCrudRepository<Predictor> {

    Optional<Predictor> findByLogin(String login);

    Optional<Predictor> findByLoginAndPassword(String login, String password);

    Optional<Predictor> findByAuthenticationToken(String token);

    default Optional<Predictor> findByLoginAndPassword(AuthenticationDetails authenticationDetails) {
        return findByLoginAndPassword(authenticationDetails.getLogin(), authenticationDetails.getPassword());
    }
}
