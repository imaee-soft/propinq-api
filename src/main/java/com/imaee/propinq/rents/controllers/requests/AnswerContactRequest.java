package com.imaee.propinq.rents.controllers.requests;

import com.imaee.propinq.buildings.pipes.ValidEnum;
import com.imaee.propinq.rents.data.enums.ContactState;
import jakarta.validation.constraints.NotBlank;

public record AnswerContactRequest(
        @NotBlank(message = "La respuesta a la solicitud de contacto no puede estar vacía")
        String answer,

        @NotBlank(message = "El nuevo estado del contacto no puede estar vacío")
        @ValidEnum(enumClass = ContactState.class, message = "El nuevo estado debe ser uno de los siguientes: REJECTED, ACCEPTED")
        String newState
) {}