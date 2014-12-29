package controllers.validation;

import models.Predictor;
import models.PredictorRepository;
import play.libs.Json;
import play.mvc.Http;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

/**
 * Base business validator for predictor services.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public abstract class AbstractBusinessValidator<T> implements BusinessValidator {

    private final ValidationResult result = new ValidationResult();

    @Inject
    private PredictorRepository predictorRepository;

    private T inputData;
    private Predictor currentUser;

    @Override
    public final ValidationResult validate(Http.Request request) {
        inputData = Json.fromJson(request.body().asJson(), getInputDataClass());
        Optional.ofNullable(request.username()).ifPresent(
                u -> currentUser = predictorRepository.findByLogin(u).get()
        );

        result.getMessages().clear();
        validationLogic();
        return result;
    }

    @Override
    public Predictor getCurrentUser() {
        return currentUser;
    }

    protected T getInputData() {
        return inputData;
    }

    protected Class<T> getInputDataClass() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    protected PredictorRepository getPredictorRepository() {
        return predictorRepository;
    }

    protected abstract void validationLogic();

    protected void addMessage(String key, String msg) {
        result.addMessage(key, msg);
    }
}
