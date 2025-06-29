package com.imaee.propinq.neighborhood.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LocalityRequest(
        @NotNull
        @Size(min = 2, max = 64, message = "EL NOMBRE DEBE IR DE ENTRE 2 Y 64 CARACTERES")
        @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚ]+(\\s[a-zA-Z0-9áéíóúÁÉÍÓÚ]+)*$", message = "EL NOMBRE SÓLO DEBE TENER LETRAS Y NÚMEROS, NO DEBEN HABER ESPACIOS DE MÁS NI TAMPOCO AL INICIO NI AL FINAL")
        String name,

        @NotNull
        Integer provinceId
) {
}
