package com.imaee.propinq.users.controllers.responses;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponse(
        String dni,
        LocalDate birthDate,
        String fullName,
        String email,
        String address,
        String phoneNumber,
        String cuit
) {}