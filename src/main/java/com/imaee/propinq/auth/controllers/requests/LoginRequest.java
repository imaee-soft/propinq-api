package com.imaee.propinq.auth.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.imaee.propinq.shared.Patterns.EMAIL_PATTERN;

public record LoginRequest(

        @NotBlank(message = "El email del usuario no debe estar vacío")
        @Pattern(regexp = EMAIL_PATTERN, message = "El email ingresado es inválido")
        String email,

        @NotBlank(message = "La contraseña no debe estar vacía")
        String password,

        String recaptchaToken
) {}