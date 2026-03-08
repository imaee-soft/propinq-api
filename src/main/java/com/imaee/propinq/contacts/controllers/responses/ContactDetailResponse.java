package com.imaee.propinq.contacts.controllers.responses;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ContactDetailResponse(
        UUID contactId,
        UUID propertyId,
        LocalDateTime contactDate,
        String message,
        LocalDateTime answerDate,
        String answer,
        String owner,
        String ownerPhoneNumber,
        String issuer,
        String propertyAddress,
        String status,
        Double latitude,
        Double longitude,
        boolean isOwnerRetrieving,
        String issuerPhoneNumber,
        LocalDateTime cancellationDate,
        String cancellationReason
) {}