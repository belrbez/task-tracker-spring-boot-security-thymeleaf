package com.epam.company.util.resolvers;

/**
 * Created by @belrbeZ
 */
public class ErrorMessageResolver {

    public static final String NOT_FOUND = "MODEL NOT FOUND ";
    public static final String NULLABLE_ID = "ID is NULL ";
    public static final String NULLABLE_MODEL = "MODEL is NULLABLE ";
    public static final String NULLABLE_LOGIN = "LOGIN is NULL ";

    private static final String OPT_CREATE  = "During CREATE ";
    private static final String OPT_UPDATE  = "During UPDATE ";
    private static final String OPT_GET     = "During GET ";
    private static final String OPT_REMOVE  = "During REMOVE ";

    private static final String PARAM_ID    = "param ID was ";
    private static final String PARAM_MODEL = "param MODEL was ";

    public static final String GET_NOT_FOUND    = OPT_GET + NOT_FOUND;
    public static final String UPDATE_NOT_FOUND = OPT_UPDATE + NOT_FOUND;
    public static final String REMOVE_NOT_FOUND = OPT_REMOVE + NOT_FOUND;

    public static final String GET_NULLABLE_ID      = OPT_GET + NULLABLE_ID;
    public static final String UPDATE_NULLABLE_ID   = OPT_UPDATE + PARAM_ID + NULLABLE_ID;
    public static final String CREATE_NULLABLE_ID   = OPT_CREATE + PARAM_ID + NULLABLE_ID;
    public static final String REMOVE_NULLABLE_ID   = OPT_REMOVE + PARAM_ID + NULLABLE_ID;

    public static final String UPDATE_MODEL_NULLABLE = OPT_UPDATE + PARAM_MODEL + NULLABLE_MODEL;
    public static final String CREATE_MODEL_NULLABLE = OPT_CREATE + PARAM_MODEL + NULLABLE_MODEL;
    public static final String REMOVE_MODEL_NULLABLE = OPT_REMOVE + PARAM_MODEL + NULLABLE_MODEL;

    public static final String GET_NULLABLE_LOGIN = OPT_GET + NULLABLE_LOGIN;
}
