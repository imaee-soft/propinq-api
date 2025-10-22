package com.imaee.propinq.properties.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PropertyTypeRequest(
        @NotNull
        @Size(min = 2, max = 64, message = "El nombre debe ir entre 2 y 64 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚ()]+(\\s[a-zA-Z0-9áéíóúÁÉÍÓÚ()]+)*$", message = "El nombre sólo debe tener letras, números o paréntesis y no pueden haber espacios sobrantes ni tampoco al inicio ni al final")
        String name,

        @NotNull
        String description
) {
}
