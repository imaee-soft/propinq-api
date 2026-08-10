package com.imaee.propinq.buildings.controllers.requests;

import com.imaee.propinq.buildings.data.enums.BuildingType;
import com.imaee.propinq.buildings.pipes.ValidEnum;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * Redefines the Default group so presence checks run before format/size checks.
 * Empty values therefore surface {@code @NotBlank}/{@code @NotNull} messages only.
 *
 * @see <a href="https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-default-group-class">
 * Hibernate Validator — redefining the default group sequence for a class</a>
 */
@GroupSequence({CreateBuildingRequest.class, CreateBuildingRequest.Extended.class})
public record CreateBuildingRequest(

        @NotBlank(message = "El nombre del edificio no debe estar vacío")
        @Length(min = 5, groups = Extended.class, message = "El nombre del edificio debe tener al menos 5 caracteres")
        String name,

        @Length(max = 125, groups = Extended.class, message = "La descripción del edificio debe tener como máximo 125 caracteres")
        String description,

        @NotBlank(message = "La dirección del edificio no debe estar vacía")
        @Length(min = 5, groups = Extended.class, message = "La dirección del edificio debe tener al menos 5 caracteres")
        String address,

        @NotNull(message = "La latitud del edificio no debe ser nula")
        Double latitude,

        @NotNull(message = "La longitud del edificio no debe ser nula")
        Double longitude,

        @NotNull(message = "El ID del usuario no debe ser nulo")
        UUID userId,

        @NotBlank(message = "El tipo de edificio no debe estar vacío")
        @ValidEnum(groups = Extended.class, enumClass = BuildingType.class, message = "El tipo del edificio debe ser uno de los siguientes: EDIFICIO, COMPLEJO")
        String type
) {
    public interface Extended {
    }
}
