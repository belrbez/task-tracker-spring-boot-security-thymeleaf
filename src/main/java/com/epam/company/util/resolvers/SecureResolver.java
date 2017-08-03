package com.epam.company.util.resolvers;


import com.epam.company.model.types.UserProfileType;

/**
 * Created by @belrbeZ
 */
public class SecureResolver {
    public static final String ROLE_ADMIN           = "ADMIN";
    public static final String ROLE_USER            = "USER";

    // In Seconds
    public static final Integer ACCESS_TOKEN_PERIOD  = 240;
    public static final Integer REFRESH_TOKEN_PERIOD = 3600;

    public static String convert(UserProfileType type) {
        switch (type) {
            case ADMIN:
                return ROLE_ADMIN;
            default:
                return ROLE_USER;
        }
    }
}
