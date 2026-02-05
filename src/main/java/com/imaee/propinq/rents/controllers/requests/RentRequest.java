package com.imaee.propinq.rents.controllers.requests;

import com.imaee.propinq.buildings.pipes.ValidEnum;
import com.imaee.propinq.rents.data.enums.RaiseIndex;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RentRequest(

        @NotNull(message = "El contacto del alquiler es obligatorio")
        UUID contactId,

        @NotNull(message = "La fecha del alquiler es obligatoria")
        LocalDate date,

        @NotNull(message = "La duración del alquiler es obligatoria")
        @Min(value = 0, message = "La duración del alquiler no puede ser negativa")
        Integer yearsDuration,

        @NotNull(message = "El día límite de pago es obligatorio")
        @Min(value = 0, message = "El día límite de pago no puede ser negativo")
        @Max(value = 28, message = "El día límite de pago no puede ser mayor a 28")
        Integer payday,

        @NotNull(message = "El precio del alquiler no debe ser nulo")
        @Min(value = 0, message = "El precio del alquiler no puede ser negativo")
        Double price,

        @NotBlank(message = "El índice de aumento no puede estar vacío")
        @ValidEnum(enumClass = RaiseIndex.class, message = "El índice de aumento debe ser uno de los siguientes: ICL, IPC, CasaPropia, IS")
        String raiseIndex,

        @NotNull(message = "Los meses de aumento del alquiler son obligatorios")
        @Min(value = 0, message = "Los meses de aumento del alquiler no pueden ser negativos")
        Integer raiseMonths
) {}