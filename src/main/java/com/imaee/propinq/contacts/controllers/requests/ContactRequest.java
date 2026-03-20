package com.imaee.propinq.contacts.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ContactRequest(

        @NotNull(message = "La propiedad contactada es obligatoria")
        UUID propertyId,

        @NotBlank(message = "El mensaje de la solicitud del contacto es obligatorio")
        String message
) {}