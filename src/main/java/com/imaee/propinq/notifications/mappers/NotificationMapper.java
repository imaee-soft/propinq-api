package com.imaee.propinq.notifications.mappers;

import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import com.imaee.propinq.notifications.data.enums.NotificationType;
import com.imaee.propinq.notifications.data.models.Notification;
import com.imaee.propinq.users.data.models.User;

public class NotificationMapper {

    public static Notification buildNotification(
            User notifier,
            NotificationType notificationType,
            String title,
            String url
    ) {
        return buildNotification(notifier, null, notificationType, title, null, url);
    }

    public static Notification buildNotification(
            User notifier,
            User notified,
            NotificationType notificationType,
            String title,
            String description,
            String url
    ) {
        return Notification.builder()
                .notifier(notifier)
                .notified(notified)
                .notificationType(notificationType)
                .title(title)
                .description(description)
                .url(url)
                .build();
    }

    public static NotificationResponse buildNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .type(notification.getNotificationType().name())
                .title(notification.getTitle())
                .description(notification.getDescription())
                .seen(notification.isSeen())
                .notifierFullName(notification.getNotifier().getFullName())
                .notifierUserId(notification.getNotifier().getUserId())
                .createdAt(notification.getCreatedAt())
                .url(notification.getUrl())
                .build();
    }
}