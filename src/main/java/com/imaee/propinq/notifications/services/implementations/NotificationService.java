package com.imaee.propinq.notifications.services.implementations;

import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import com.imaee.propinq.notifications.data.models.Notification;
import com.imaee.propinq.notifications.data.repositories.INotificationRepository;
import com.imaee.propinq.notifications.mappers.NotificationMapper;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {

    private final INotificationRepository notificationRepository;

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> getUserNotifications(UUID userId) {
        return notificationRepository.findByNotifiedUserId(userId).stream()
                .map(NotificationMapper::buildNotificationResponse)
                .toList();
    }
}