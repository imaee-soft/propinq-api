package com.imaee.propinq.buildings.controllers.requests;

import com.imaee.propinq.buildings.data.enums.BuildingType;
import com.imaee.propinq.buildings.pipes.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record UpdateBuildingRequest(

        @NotBlank(message = "El nombre del edificio no debe estar vacío")
        @Length(min = 5, message = "El nombre del edificio debe tener al menos 5 caracteres")
        String name,

        @NotBlank(message = "La descripción del edificio no debe estar vacía")
        @Length(max = 400, message = "La descripción del edificio debe tener como máximo 400 caracteres")
        String description,

        @NotBlank(message = "El tipo de edificio no debe estar vacío")
        @ValidEnum(enumClass = BuildingType.class, message = "El tipo del edificio debe ser uno de los siguientes: EDIFICIO, COMPLEJO")
        String type,

        List<
                @NotBlank(message = "El enlace de la imagen no debe estar vacío")
                @URL(message = "Debe proporcionar una URL válida")
                String> existingImagesURLS
        )
{}
