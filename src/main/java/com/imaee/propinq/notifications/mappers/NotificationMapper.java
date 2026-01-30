package com.imaee.propinq.notifications.mappers;

import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import com.imaee.propinq.notifications.data.enums.NotificationType;
import com.imaee.propinq.notifications.data.models.Notification;
import com.imaee.propinq.rents.data.models.Contact;
import com.imaee.propinq.users.data.models.User;

public class NotificationMapper {

    public static Notification buildNotification(
            User notifier,
            User notified,
            NotificationType notificationType,
            String title,
            String description,
            String url
    ) {
        return buildNotification(notifier, notified, null, notificationType, title, description, url);
    }

    public static Notification buildNotification(
            User notifier,
            User notified,
            Contact contact,
            NotificationType notificationType,
            String title,
            String description,
            String url
    ) {
        return Notification.builder()
                .notifier(notifier)
                .notified(notified)
                .contact(contact)
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
                .contactId(notification.getContact() != null ? notification.getContact().getContactId() : null)
                .createdAt(notification.getCreatedAt())
                .url(notification.getUrl())
                .build();
    }
}