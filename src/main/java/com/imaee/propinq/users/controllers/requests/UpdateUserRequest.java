package com.imaee.propinq.users.controllers.requests;

import com.imaee.propinq.users.data.pipes.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotNull(message = "El DNI del usuario no debe ser nulo")
        @Size(min = 1, max = 8, message = "El DNI del usuario debe tener entre 1 y 8 caracteres")
        String dni,

        @NotNull(message = "El nombre del usuario no debe ser nulo")

        @Size(min = 3, max = 20, message = "El nombre del usuario debe tener entre 3 y 20 caracteres")
        String firstName,

        @NotNull(message = "El apellido del usuario no debe ser nulo")
        @Size(min = 3, max = 20, message = "El apellido del usuario debe tener entre 3 y 20 caracteres")
        String lastName,

        @NotNull(message = "La dirección del usuario no debe ser nula")
        @Size(min = 3, max = 50, message = "La dirección del usuario debe tener entre 3 y 50 caracteres")
        String address,

        @NotBlank(message = "El número de teléfono del usuario no debe estar en blanco")
        @PhoneNumber(message = "El número de teléfono del usuario debe ser válido")
        String phoneNumber
) {}