package controllers.validation;

import javax.inject.Named;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Named
public class EmptyValidator extends AbstractBusinessValidator<Object> {

    @Override
    protected void validationLogic(Object data) {
    }
}
