package com.imaee.propinq.auth.controllers.requests;

import com.imaee.propinq.users.data.pipes.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.imaee.propinq.shared.Patterns.EMAIL_PATTERN;

public record SignUpRequest(

        @NotNull(message = "User dni must not be null")
        @Size(max = 8, message = "User DNI must be between 0 and 8 characters")
        String dni,

        @NotNull(message = "User birth date must not be null")
        @Size(min = 10, max = 10, message = "User birth date must be in the format YYYY-MM-DD")
        String birthDate,

        @NotNull(message = "User password must not be null")
        @Size(min = 6, max = 20, message = "User password must be between 6 and 20 characters")
        String password,

        @NotNull(message = "User first name must not be null")
        @Size(min = 3, max = 20, message = "User first name must be between 3 and 20 characters")
        String firstName,

        @NotNull(message = "User last name must not be null")
        @Size(min = 3, max = 20, message = "User last name must be between 3 and 20 characters")
        String lastName,

        @NotNull(message = "User email must not be null")
        @Pattern(regexp = EMAIL_PATTERN, message = "User email must be a valid email")
        String email,

        @NotNull(message = "User address must not be null")
        @Size(min = 3, max = 50, message = "User address must be between 3 and 50 characters")
        String address,

        @NotBlank(message = "User phone number must not be blank")
        @PhoneNumber(message = "User phone number must be a valid phone number")
        String phoneNumber
) {}