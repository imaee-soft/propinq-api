package com.imaee.propinq.notifications.controllers.implementations;

import com.imaee.propinq.notifications.controllers.interfaces.INotificationController;
import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class NotificationController implements INotificationController {

    private final INotificationService notificationService;

    @Override
    public Page<NotificationResponse> getUserNotifications(UUID userId, Integer pageNumber, Integer pageSize) {
        return notificationService.getUserNotifications(userId, pageNumber, pageSize);
    }

    @Override
    public List<NotificationResponse> getUserNewNotifications(UUID userId) {
        return notificationService.getUserNewNotifications(userId);
    }

    @Override
    public void markAsSeen(UUID notificationId) {
        notificationService.markAsSeen(notificationId);
    }

    @Override
    public void markAsUnseen(UUID notificationId) {
        notificationService.markAsUnseen(notificationId);
    }
}