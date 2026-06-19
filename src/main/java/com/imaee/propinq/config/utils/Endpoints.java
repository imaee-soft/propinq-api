package com.imaee.propinq.config.utils;

public class Endpoints {

    private Endpoints() {}

    public static final String API = "/api/v1";

    public static final String AUTH_ENDPOINTS = API + "/auth/**";

    public static final String PARAMETERS_ENDPOINTS = API + "/parameters/**";

    public static final String[] USER_ACTIVATION_ENDPOINTS = {
            API + "/users/activate",
            API + "/users/resend-activation-email",
            API + "/users/send-new-activation-token",
    };

    public static final String[] DOC_ENDPOINTS = {
        "/docs",
        "/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html"
    };

    public static final String[] RETRIEVE_ENDPOINTS = {
            API + "/buildings/**",
            API + "/properties/**",
            API + "/provinces/**",
            API + "/localities/**",
            API + "/pois/**",
            API + "/neighborhoods/**",
    };
}