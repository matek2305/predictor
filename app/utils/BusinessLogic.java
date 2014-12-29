package utils;

import controllers.validation.BusinessValidator;
import controllers.validation.EmptyValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Business logic mark.
 *
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BusinessLogic {

    /**
     * Business validator for marked method.
     * @return
     */
    public Class<? extends BusinessValidator<?>> validator() default EmptyValidator.class;

    /**
     * Value defining whether authentication is required for marked method or not.
     * @return
     */
    public boolean authenticate() default true;
}
