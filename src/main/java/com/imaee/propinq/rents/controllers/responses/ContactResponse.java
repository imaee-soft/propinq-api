package com.imaee.propinq.rents.controllers.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ContactResponse(
        UUID contactId,
        String issuerFullName,
        String message,
        String contactState
) {}