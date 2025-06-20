package com.imaee.propinq.properties.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record PropertyTypeRequest(
        @NotNull
        @Size(min=2, max = 64, message = "EL NOMBRE DEBE TENER ENTRE 2 Y 64 CARACTERES")
        @Pattern(regexp = "^[A-Za-z0-9]+( [A-Za-z0-9]+)*$", message = "SÓLO PUEDEN HABER LETRAS Y NUMEROS, NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL, NO PUEDE HABER CARACTERES ESPECIALES")
        String name,

        @NotNull
        @Size(min=2, max = 64, message = "LA DESCRIPCION DEBE TENER ENTRE 2 Y 64 CARACTERES")
        @Pattern(regexp = "^[A-Za-z0-9]+( [A-Za-z0-9]+)*$", message = "SÓLO PUEDEN HABER LETRAS Y NUMEROS, NO TIENE QUE HABER ESPACIOS DOBLES NI ESPACIO AL INICIO NI AL FINAL, NO PUEDE HABER CARACTERES ESPECIALES")
        String description

        ) {
}
