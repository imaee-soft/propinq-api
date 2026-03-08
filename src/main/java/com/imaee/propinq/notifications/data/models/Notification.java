package com.imaee.propinq.notifications.data.models;

import com.imaee.propinq.notifications.data.enums.NotificationType;
import com.imaee.propinq.users.data.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity(name="notifications")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Notification {

    @Id
    private final UUID notificationId = UUID.randomUUID();

    @ManyToOne
    private User notifier;

    @ManyToOne
    @NotNull
    private User notified;

    @Enumerated(STRING)
    private NotificationType notificationType;

    private String title;
    private String description;
    private String url;

    @Builder.Default
    private boolean seen = false;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}