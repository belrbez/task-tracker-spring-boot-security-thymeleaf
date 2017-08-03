package com.epam.company.util.annotation.impl;

import com.epam.company.util.Validator;
import com.epam.company.util.annotation.Login;
import com.epam.company.util.annotation.Pass;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidatorConstraint implements ConstraintValidator<Login, String> {
    @Override
    public void initialize(Login login) { }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return Validator.isLoginValid(login);
    }
}
