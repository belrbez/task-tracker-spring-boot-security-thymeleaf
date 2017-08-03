package com.epam.company.util.annotation;

import com.epam.company.util.annotation.impl.PhoneValidatorConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by @belrbeZ
 */

@Documented
@Constraint(validatedBy = PhoneValidatorConstraint.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "{Phone is invalid!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
