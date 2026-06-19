package com.imaee.propinq.notifications.controllers.interfaces;

import com.imaee.propinq.config.utils.Endpoints;

import com.imaee.propinq.notifications.controllers.packages.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping(Endpoints.API_V1 + "/notifications")
@Tag(
        name = "Notifications",
        description = "Operations for managing user notifications managed inside the application."
)
public interface INotificationController {

    @GetMapping("/user-all/{userId}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves the specified user notifications.")
    Page<NotificationResponse> getUserNotifications(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
            @RequestParam(defaultValue = "6", name = "size") Integer pageSize
    );

    @GetMapping("/user/{userId}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves the specified user unseen notifications.")
    List<NotificationResponse> getUserNewNotifications(@PathVariable UUID userId);

    @PatchMapping("/see/{notificationId}")
    @ResponseStatus(OK)
    @Operation(summary = "Marks the notification as seen.")
    void markAsSeen(@PathVariable UUID notificationId);

    @PatchMapping("/unsee/{notificationId}")
    @ResponseStatus(OK)
    @Operation(summary = "Marks the notification as unseen.")
    void markAsUnseen(@PathVariable UUID notificationId);
}