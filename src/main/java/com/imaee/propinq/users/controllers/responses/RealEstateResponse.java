package com.imaee.propinq.users.controllers.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RealEstateResponse(
        UUID userId,
        String username,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        String companyName,
        String cuit,
        String legalName,
        String role
) {}

