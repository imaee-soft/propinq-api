package com.imaee.propinq.rents.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record RentDocumentRequest(

        @NotNull(message = "El ID del alquiler no puede ser nulo")
        UUID rentId,

        @NotBlank(message = "El nombre del documento no debe estar vacío")
        @Length(min = 5, message = "El nombre del documento debe tener al menos 5 caracteres")
        String name
) {}