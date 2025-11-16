package com.imaee.propinq.notifications.controllers.interfaces;

import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/notifications")
@Tag(
        name = "Notifications",
        description = "Operations for managing user notifications managed inside the application."
)
public interface INotificationController {

    @GetMapping("/user/{userId}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves the specified user notifications.")
    List<NotificationResponse> getUserNotifications(@PathVariable UUID userId);
}