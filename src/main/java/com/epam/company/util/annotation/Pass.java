package com.epam.company.util.annotation;


import com.epam.company.util.annotation.impl.PassValidatorConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Default Comment
 *
 * @author  @belrbeZ
 * @since 29.08.2017
 */
@Documented
@Constraint(validatedBy = PassValidatorConstraint.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pass {
    String message() default "{Password is invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
