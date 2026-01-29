package com.imaee.propinq.notifications.controllers.implementations;

import com.imaee.propinq.notifications.controllers.interfaces.INotificationController;
import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class NotificationController implements INotificationController {

    private final INotificationService notificationService;

    @Override
    public List<NotificationResponse> getUserNotifications(UUID userId) {
        return notificationService.getUserNotifications(userId);
    }

    @Override
    public void markAsSeen(UUID notificationId) {
        notificationService.markAsSeen(notificationId);
    }
}