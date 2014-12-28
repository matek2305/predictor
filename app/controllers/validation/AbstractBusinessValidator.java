package controllers.validation;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Hatake on 2014-12-26.
 */
public abstract class AbstractBusinessValidator<T> implements BusinessValidator<T> {

    private final ValidationResult result = new ValidationResult();

    @Override
    public final boolean validate(T data) {
        result.getMessages().clear();
        validationLogic(data);
        return result.getMessages().isEmpty();
    }

    @Override
    public final Class<T> getInputDataClass() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    protected abstract void validationLogic(T data);

    protected void addMessage(String key, String msg) {
        result.addMessage(key, msg);
    }

    @Override
    public ValidationResult getResult() {
        return result;
    }
}
