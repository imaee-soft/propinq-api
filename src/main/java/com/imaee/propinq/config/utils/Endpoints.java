package com.imaee.propinq.config.utils;

public class Endpoints {

    private Endpoints() {}

    public static final String API_V1 = "/api/v1";

    public static final String AUTH_ENDPOINTS = API_V1 + "/auth/**";

    public static final String PARAMETERS_ENDPOINTS = API_V1 + "/parameters/**";

    public static final String[] USER_ACTIVATION_ENDPOINTS = {
            API_V1 + "/users/activate",
            API_V1 + "/users/resend-activation-email",
            API_V1 + "/users/send-new-activation-token",
    };

    public static final String[] DOC_ENDPOINTS = {
        "/docs",
        "/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html"
    };

    public static final String[] RETRIEVE_ENDPOINTS = {
            API_V1 + "/buildings/**",
            API_V1 + "/properties/**",
            API_V1 + "/provinces/**",
            API_V1 + "/localities/**",
            API_V1 + "/pois/**",
            API_V1 + "/neighborhoods/**",
    };
}