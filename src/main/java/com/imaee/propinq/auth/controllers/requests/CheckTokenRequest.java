package com.imaee.propinq.auth.controllers.requests;

import jakarta.validation.constraints.NotBlank;

public record CheckTokenRequest(
        @NotBlank(message = "El access token no debe estar vacío")
        String accessToken
) {}