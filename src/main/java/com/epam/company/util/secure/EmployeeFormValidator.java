package com.epam.company.util.secure;

import com.epam.company.model.dao.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EmployeeFormValidator implements Validator {

    //which objects can be validated by this validator
    @Override
    public boolean supports(Class<?> paramClass) {
        return User.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");

        User emp = (User) obj;
        if(emp.getId() <=0){
            errors.rejectValue("id", "negativeValue", new Object[]{"'id'"}, "id can't be negative");
        }

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "role.required");
    }
}