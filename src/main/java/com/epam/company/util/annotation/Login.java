package com.epam.company.util.annotation;

import com.epam.company.util.annotation.impl.LoginValidatorConstraint;
import com.epam.company.util.annotation.impl.PassValidatorConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LoginValidatorConstraint.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
    String message() default "Некорректный логин (6-12 символов)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
