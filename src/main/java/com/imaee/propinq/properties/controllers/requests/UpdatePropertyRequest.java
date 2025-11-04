package com.imaee.propinq.properties.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record UpdatePropertyRequest(
        @NotBlank(message = "El nombre de la vivienda no debe estar vacío")
        @Length(min = 5, message = "El nombre de la vivienda debe tener al menos 5 caracteres")
        String title,

        @NotBlank(message = "La descripción de la vivienda no debe estar vacía")
        @Length(max = 125, message = "La descripción de la vivienda debe tener como máximo 125 caracteres")
        String description,

        List<
                @NotBlank(message = "El enlace de la imagen no debe estar vacío")
                @URL(message = "Debe proporcionar una URL válida")
                        String> existingImagesURLS

        ,
        @NotNull(message = "El precio de la vivienda no debe estar vacío")
        Double price,

        @NotNull(message = "La cantidad de habitaciones no debe estar vacío")
        Integer bedrooms,

        @NotNull(message = "La descripción de la vivienda no debe estar vacía")
        Integer bathrooms,

        @NotNull(message = "Se debe indicar si se permiten mascotas")
        Boolean petsAllowed,

        @NotNull(message = "Se debe indicar si la vivienda está amueblada")
        Boolean furnishing,

        @NotNull(message = "Se debe indicar si se incluyen expensas")
        Boolean expenses
) {
}