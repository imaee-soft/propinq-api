package com.imaee.propinq.auth.controllers.requests;

import com.imaee.propinq.users.data.pipes.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.imaee.propinq.shared.Patterns.EMAIL_PATTERN;

public record SignUpRequest(

        @NotNull(message = "El DNI del usuario no debe ser nulo")
        @Size(max = 8, message = "El DNI del usuario debe tener entre 0 y 8 caracteres")
        String dni,

        @NotNull(message = "La fecha de nacimiento del usuario no debe ser nula")
        @Size(min = 10, max = 10, message = "La fecha de nacimiento del usuario debe tener el formato AAAA-MM-DD")
        String birthDate,

        @NotNull(message = "La contraseña del usuario no debe ser nula")
        @Size(min = 6, max = 20, message = "La contraseña del usuario debe tener entre 6 y 20 caracteres")
        String password,

        @NotNull(message = "El nombre del usuario no debe ser nulo")
        @Size(min = 3, max = 20, message = "El nombre del usuario debe tener entre 3 y 20 caracteres")
        String firstName,

        @NotNull(message = "El apellido del usuario no debe ser nulo")
        @Size(min = 3, max = 20, message = "El apellido del usuario debe tener entre 3 y 20 caracteres")
        String lastName,

        @NotNull(message = "El correo electrónico del usuario no debe ser nulo")
        @Pattern(regexp = EMAIL_PATTERN, message = "El correo electrónico del usuario debe ser válido")
        String email,

        @NotNull(message = "La dirección del usuario no debe ser nula")
        @Size(min = 3, max = 50, message = "La dirección del usuario debe tener entre 3 y 50 caracteres")
        String address,

        @NotBlank(message = "El número de teléfono del usuario no debe estar en blanco")
        @PhoneNumber(message = "El número de teléfono del usuario debe ser válido")
        String phoneNumber
) {}