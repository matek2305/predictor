package controllers.validation;

/**
 * Created by Hatake on 2014-12-25.
 */
public interface BusinessValidator<T> {

    boolean validate(T data);

    Class<T> getInputDataClass();

    ValidationResult getResult();
}
