package com.imaee.propinq.properties.controllers.requests;

import com.imaee.propinq.buildings.pipes.ValidEnum;
import com.imaee.propinq.properties.data.enums.PropertyType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record CreatePropertyRequest(

        @Length(max = 125, message = "La descripción de la vivienda debe tener como máximo 125 caracteres")
        String description,

        @Min(value = 0, message = "El precio de la vivienda no puede ser negativo")
        @NotNull(message = "El precio de la vivienda no debe ser nulo")
        Double price,

        @Min(value = 0, message = "La cantidad de habitaciones no puede ser negativa")
        @NotNull(message = "La cantidad de habitaciones no puede ser nula")
        Integer bedrooms,

        @Min(value = 0, message = "La cantidad de baños no puede ser negativa")
        @NotNull(message = "La cantidad de baños no puede ser nula")
        Integer bathrooms,

        @NotNull(message = "La aceptación de mascotas no puede ser nula")
        Boolean petsAllowed,

        @NotNull(message = "La aceptación de mascotas no puede ser nula")
        Boolean hasFurniture,

        @NotNull(message = "La aceptación de mascotas no puede ser nula")
        Boolean paysExpenses,

        @NotBlank(message = "El tipo de propiedad no debe estar vacío")
        @ValidEnum(enumClass = PropertyType.class, message = "El tipo de propiedad debe ser uno de los siguientes: APARTAMENTO, CASA")
        String type,

        Integer floor,
        String number,
        String address,
        Double latitude,
        Double longitude,
        UUID buildingId,
        UUID userId
) {}