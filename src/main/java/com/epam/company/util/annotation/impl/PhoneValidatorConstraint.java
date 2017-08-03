package com.epam.company.util.annotation.impl;

import com.epam.company.util.Validator;
import com.epam.company.util.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by @belrbeZ
 */
public class PhoneValidatorConstraint implements ConstraintValidator<Phone, String> {
    @Override
    public void initialize(Phone phone) { }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return Validator.isPhoneValid(phone);
    }
}
