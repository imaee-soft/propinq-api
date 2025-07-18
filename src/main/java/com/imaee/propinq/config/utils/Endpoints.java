package com.imaee.propinq.config.utils;

public class Endpoints {

    private Endpoints() {}

    public static final String AUTH_ENDPOINTS = "/auth/**";

    public static final String[] DOC_ENDPOINTS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
    };
}