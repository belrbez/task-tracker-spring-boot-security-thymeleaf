package com.epam.company.util.annotation.impl;



import com.epam.company.util.Validator;
import com.epam.company.util.annotation.Pass;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Default Comment
 *
 * @author @belrbeZ
 * @since 29.08.2017
 */
public class PassValidatorConstraint implements ConstraintValidator<Pass, String> {

        @Override
        public void initialize(Pass pass) { }

        @Override
        public boolean isValid(String pass, ConstraintValidatorContext constraintValidatorContext) {
                return Validator.isPassValid(pass);
        }
}
