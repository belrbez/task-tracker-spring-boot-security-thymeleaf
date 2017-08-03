package com.epam.company.util.resolvers;

/**
 * Created by @belrbeZ
 */
public class TemplateResolver {

    public static final String REGISTER = "registration";
    public static final String LOGIN    = "login";
    public static final String DENIED    = "denied";


    public static final String WELCOME  = "welcome";
    public static final String HOME     = "home";
    public static final String PROFILE  = "profile";
    public static final String TASK     = "task";
    public static final String FEED     = "feed";
    public static final String MAP      = "map";
    public static final String TASK_FORM = "taskform";
    public static final String GEOPOINT_FORM = "geopointform";

    public static String redirect(String viewName) {
        return "redirect:" + viewName;
    }
}
