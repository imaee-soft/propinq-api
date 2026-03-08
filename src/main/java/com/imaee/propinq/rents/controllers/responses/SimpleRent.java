package com.imaee.propinq.rents.controllers.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record SimpleRent(
        UUID rentId,
        UUID propertyId,
        LocalDate rentDate,
        String tenantFullName,
        String ownerFullName,
        String propertyName,
        Double latitude,
        Double longitude,
        String state
) {}