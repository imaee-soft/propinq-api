package com.imaee.propinq.config.utils;

public class Endpoints {

    private Endpoints() {}

    public static final String AUTH_ENDPOINTS = "/auth/**";

    public static final String[] USER_ACTIVATION_ENDPOINTS = {
            "/api/v1/users/*/activate",
            "/resend-activation-email",
            "/send-new-activation-token",
    };

    public static final String[] DOC_ENDPOINTS = {
        "/docs",
        "/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html"
    };

    public static final String[] RETRIEVE_ENDPOINTS = {
            "/api/v1/buildings/**",
            "/api/v1/properties/**",
            "/api/v1/provinces/**",
            "/api/v1/localities/**",
            "/api/v1/pois/**",
    };
}