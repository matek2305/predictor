package controllers.validation;

import domain.entity.Predictor;
import play.mvc.Http;

/**
 * Default validator (does nothing).
 * Should never be called.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public abstract class EmptyValidator implements BusinessValidator {

    @Override
    public ValidationResult validate(Http.Request request, ValidationContext context) {
        throw new IllegalStateException("EmptyValidator called!");
    }

    @Override
    public Predictor getCurrentUser() {
        throw new IllegalStateException("EmptyValidator called!");
    }
}
