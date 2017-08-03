package com.epam.company.util;

import com.epam.company.model.dto.UserFormDTO;
import com.epam.company.util.annotation.Login;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by @belrbeZ
 */
public class Validator {

    // VALIDATOR MULTIPLE CLASSES
    static final int EMAIL_INCORRECT = -1;

    public static boolean isIdValid(Long id) {
        return id != null && id > 0;
    }

    public static boolean isStrEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }

    public static boolean isPassValid(String pass) {
        return (pass != null && pass.trim().isEmpty() && pass.length() > 6);
    }

    public static boolean isEmailValid(String email) {
        return (email != null && !email.trim().isEmpty() && email.length() > 3 && email.indexOf('@') != EMAIL_INCORRECT);
    }

    public static int isEmailValidReturnIndex(String email) {
        return (email != null && !email.trim().isEmpty() && email.length() > 3) ? email.indexOf('@') : EMAIL_INCORRECT;
    }

    private static Predicate<String> phoneFilter = (Validator::isPhoneValid);

    public static boolean isPhoneValid(String phone) {
        return !(phone == null || phone.isEmpty())
                //validate phone numbers of format "1234567890"
                && (phone.matches("\\d{10}")
                //validating phone number with -, . or spaces
                || phone.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
                //validating phone number with extension length from 3 to 5
                || phone.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")
                //validating phone number where area code is in braces ()
                || phone.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"));

    }

    public static boolean isLoginValid(String ssoId) {
        if(ssoId.length()==0) {
            return true;
        } else if (ssoId.length() < 6 || ssoId.length() > 15) {
//            errors.rejectValue("ssoId", "Login is too short or large (6-15");
            return false;
        } else
            return true;
    }

    private static Predicate<String> geoFilter = (Validator::isGeoValid);

    public static boolean isGeoValid(String geo) {
        return !isStrEmpty(geo)
                && geo.matches("^(-?[1-8]?\\d(?:\\\\.\\\\d{1,18})?|90(?:\\\\.0{1,18})?)$|^(-?(?:1[0-7]|[1-9])?\\d(?:\\.\\d{1,18})?|180(?:\\.0{1,18})?)$");
    }

    public static boolean isGeoDoubleValid(List<Double> geos) {
        return geos.stream().map(Object::toString).noneMatch(Validator.geoFilter);
    }

    public static boolean isGeoValid(List<String> geos) {
        return geos.stream().noneMatch(Validator.geoFilter);
    }

    public static List<Double> convertGeoArrayToList(String[] points) {
        return Arrays.stream(points).map(Double::valueOf).collect(Collectors.toList());
    }
}
