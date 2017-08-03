package com.epam.company.util.resolvers;

/**
 * Created by @belrbeZ
 *
 * Stores all paths for Controllers and Rest End Points
 */
public class WebResolver {
    public static final String SECURED  = "/secure";
    private static final String OAUTH    = "/oauth";
    private static final String TOKEN    = "/token";

    public static final String ERROR    = "/error";
    public static final String DENIED   = "/denied";

    public static final String LOGIN    = "/login";
    public static final String REGISTER = "/register";
    public static final String WELCOME  = "/welcome";

    public static final String PROFILE  = SECURED + "/profile";
    public static final String LOGOUT   = SECURED + "/logout";
    public static final String TASK     = SECURED + "/task";
    public static final String TASK_FORM = TASK + "/form";
    public static final String TASK_SUBS = TASK + "/subscribe";
    public static final String TASK_UNSUBS =  TASK + "/unsubscribe";
    public static final String TASK_COMMENT = "/comment";

    public static final String FEED     = SECURED + "/feed";
    public static final String FEED_FILTER = FEED + "/filter";
    public static final String FEED_SEARCH = FEED + "/searchByTheme";

    public static final String ADMIN     = "/admin";
    public static final String ADMIN_NEW_USER     = ADMIN + "/newUser";
    public static final String ADMIN_DELETE_USER     = ADMIN + "/deleteUser";
    public static final String ADMIN_CHANGE_USER     = ADMIN + "/editUser";


}
