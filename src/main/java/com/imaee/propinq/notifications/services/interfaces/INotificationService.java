package com.imaee.propinq.notifications.services.interfaces;

import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import com.imaee.propinq.notifications.data.models.Notification;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface INotificationService {
    Page<NotificationResponse> getUserNotifications(UUID userId, Integer pageNumber, Integer pageSize);
    List<NotificationResponse> getUserNewNotifications(UUID userId);
    void saveNotification(Notification notification);
    void markAsSeen(UUID notificationId);
    void markAsUnseen(UUID notificationId);
}