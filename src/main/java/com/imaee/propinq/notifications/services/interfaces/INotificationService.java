package com.imaee.propinq.notifications.services.interfaces;

import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import com.imaee.propinq.notifications.data.models.Notification;

import java.util.List;
import java.util.UUID;

public interface INotificationService {
    void saveNotification(Notification notification);
    void markAsSeen(UUID notificationId);
    List<NotificationResponse> getUserNotifications(UUID userId);
}