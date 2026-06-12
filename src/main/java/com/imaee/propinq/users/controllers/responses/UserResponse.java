package com.imaee.propinq.users.controllers.responses;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponse(
        LocalDate birthDate,
        String firstName,
        String lastName,
        String email,
        String address,
        String phoneNumber
) {}