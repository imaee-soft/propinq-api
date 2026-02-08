package com.imaee.propinq.rents.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RentDocumentRequest(
        @NotBlank(message = "El nombre del documento no debe estar vacío")
        @Length(min = 5, message = "El nombre del documento debe tener al menos 5 caracteres")
        String name
) {}