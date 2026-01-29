package com.imaee.propinq.notifications.services.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import com.imaee.propinq.notifications.data.models.Notification;
import com.imaee.propinq.notifications.data.repositories.INotificationRepository;
import com.imaee.propinq.notifications.mappers.NotificationMapper;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {

    private final IAuthenticatedUserService authenticatedUserService;
    private final INotificationRepository notificationRepository;

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public void markAsSeen(UUID notificationId) {
        final var notification = findByIdOrThrowException(notificationId);
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    private Notification findByIdOrThrowException(UUID notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));
    }

    @Override
    public List<NotificationResponse> getUserNotifications(UUID userId) {
        throwExceptionIfUserRetrievedIsNotLoggedUser(userId);
        return notificationRepository.findByNotifiedUserIdAndSeenFalse(userId).stream()
                .map(NotificationMapper::buildNotificationResponse)
                .toList();
    }

    private void throwExceptionIfUserRetrievedIsNotLoggedUser(UUID userId) {
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        if (!loggedUser.getUserId().equals(userId))
            throw new ResponseStatusException(FORBIDDEN);
    }
}