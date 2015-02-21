package controllers.validation;

import domain.entity.Predictor;
import play.mvc.Http;

/**
 * Business validator interface.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public interface BusinessValidator {

    /**
     * Starts validation of given request.
     * @param request request
     * @param context validation context
     * @return validation result
     */
    ValidationResult validate(Http.Request request, ValidationContext context);

    /**
     * Returns current user.
     * @return user instance
     */
    Predictor getCurrentUser();
}
