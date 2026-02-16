package com.imaee.propinq.users.controllers.responses;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ProfileChangeResponse(
        UUID profileChangeId,
        String roleRequested,
        String profileChangeState,
        LocalDateTime createdAt,
        String userFullName,
        String userPhoneNumber
) {}