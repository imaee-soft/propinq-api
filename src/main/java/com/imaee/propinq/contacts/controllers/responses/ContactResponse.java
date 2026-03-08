package com.imaee.propinq.contacts.controllers.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ContactResponse(
        UUID contactId,
        String issuerFullName,
        String message,
        String contactState
) {}