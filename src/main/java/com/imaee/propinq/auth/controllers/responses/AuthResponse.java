package com.imaee.propinq.auth.controllers.responses;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        UserAuthResponse user
) {}