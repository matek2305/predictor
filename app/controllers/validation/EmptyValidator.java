package controllers.validation;

import models.Predictor;
import play.mvc.Http;

import java.util.Optional;

/**
 * Default validator (does nothing).
 * Should never be called.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public abstract class EmptyValidator implements BusinessValidator {

    @Override
    public ValidationResult validate(Http.Request request) {
        throw new IllegalStateException("EmptyValidator called!");
    }

    @Override
    public Optional<Predictor> getCurrentUser() {
        throw new IllegalStateException("EmptyValidator called!");
    }
}
