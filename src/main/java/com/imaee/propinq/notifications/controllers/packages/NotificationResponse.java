package com.imaee.propinq.notifications.controllers.packages;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record NotificationResponse(
        UUID notificationId,
        String type,
        String title,
        String description,
        boolean seen,
        String notifierFullName,
        UUID notifierUserId,
        UUID contactId,
        LocalDateTime createdAt,
        String url
) {}