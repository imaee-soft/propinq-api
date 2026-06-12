package com.imaee.propinq.users.controllers.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ActivateUserRequest(
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El formato de email no es válido")
        String email,

        @NotBlank(message = "El código de verificación no puede estar vacío")
        String verificationCode
) {
}
