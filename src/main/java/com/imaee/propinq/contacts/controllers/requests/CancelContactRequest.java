package com.imaee.propinq.contacts.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CancelContactRequest(
        @NotBlank(message = "El motivo de la cancelación no puede estar vacío")
        @Length(max = 400, message = "El motivo de la cancelación no puede superar los 400 caracteres")
        String answer
) {}