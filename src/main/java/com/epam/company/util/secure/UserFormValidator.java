package com.epam.company.util.secure;

import com.epam.company.model.dto.UserFormDTO;
import com.epam.company.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by @belrbeZ
 */
@Component
public class UserFormValidator implements Validator {

    private final IUserService userService;

    @Autowired
    public UserFormValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserFormDTO.class);
    }


    @Override
    public void validate(Object target, Errors errors) {
        UserFormDTO form = (UserFormDTO) target;
        validateUserCredentials(errors, form);
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    public void validateUserCredentials(Errors errors, UserFormDTO form) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssoId", "NotEmpty");
        if (form.getSsoId().length() < 6 || form.getSsoId().length() > 32)
            errors.rejectValue("ssoId", "Login is too short or large");
    }

    private void validatePasswords(Errors errors, UserFormDTO form) {
        if (form.getPassword().length() < 6 || form.getPassword().length() > 32)
            errors.rejectValue("password", "Password is too short or large");

//        if (!form.getPassword().equals(form.getPasswordRepeted()))
//            errors.reject("password.no_match", "Passwords do not match");
    }

    private void validateEmail(Errors errors, UserFormDTO form) {
        if(userService.existsByEmail(form.getEmail()))
            errors.reject("email.exists", "User with this email already exists");
    }
}
