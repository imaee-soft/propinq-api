package com.imaee.propinq.rents.controllers.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record RentDetail(
    UUID rentId,
    UUID propertyId,
    UUID contactId,
    LocalDate rentDate,
    LocalDate rentDueDate,
    Integer payday,
    Double rentPrice,
    String raiseIndex,
    Integer raiseMonths,
    String tenantFullName,
    String ownerFullName,
    String propertyName,
    Double latitude,
    Double longitude,
    byte[] contract,
    List<DocumentDetail> extraDocuments,
    boolean isOwnerRetrieving,
    String rentState,
    LocalDate cancellationDate,
    String cancellationReason
) {}