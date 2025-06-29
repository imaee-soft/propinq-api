package com.imaee.propinq.properties.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record PropertyTypeRequest(
        @NotNull
        @Size(min=2, max = 64, message = "EL nombre debe tener entre 2 y 64 caracteres")
        @Pattern(regexp = "^[A-Za-z0-9]+( [A-Za-z0-9]+)*$", message = "solo pueden haber letras y numeros, no tiene que haber espacios dobles ni espacio al inicio ni al final, no puede haber caracteres especiales")
        String name,

        @NotNull
        @Size(min=2, max = 64, message = "la descripción debe tener entre 2 y 64 caracteres")
        @Pattern(regexp = "^[A-Za-z0-9]+( [A-Za-z0-9]+)*$", message = "solo pueden haber letras y numeros, no tiene que haber espacios dobles ni espacio al inicio ni al final, no puede haber caracteres especiales")
        String description

        ) {
}
