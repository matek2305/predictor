package controllers.validation;

import models.Predictor;
import play.mvc.Http;

import java.util.Optional;

/**
 * Business validator interface.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public interface BusinessValidator {

    /**
     * Starts validation of given request.
     * @param request request
     * @return validation result
     */
    ValidationResult validate(Http.Request request);

    /**
     * Returns current user (if any).
     * @return user instance
     */
    Optional<Predictor> getCurrentUser();
}
