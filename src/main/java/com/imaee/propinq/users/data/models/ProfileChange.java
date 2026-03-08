package com.imaee.propinq.users.data.models;

import com.imaee.propinq.users.data.enums.ProfileChangeState;
import com.imaee.propinq.users.data.enums.Role;
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

import static com.imaee.propinq.users.data.enums.ProfileChangeState.PENDING;
import static com.imaee.propinq.users.data.enums.Role.OWNER;
import static jakarta.persistence.EnumType.STRING;
import static java.time.LocalDateTime.now;

@Entity(name="profile_changes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProfileChange {

    @Id
    private final UUID profileChangeId = UUID.randomUUID();

    @ManyToOne
    @NotNull
    private User user;

    @Enumerated(STRING)
    @Builder.Default
    private Role roleRequested = OWNER;

    @Enumerated(STRING)
    @Builder.Default
    private ProfileChangeState state = PENDING;

    @Builder.Default
    private LocalDateTime createdAt = now();

    private LocalDateTime acceptedAt;
    private LocalDateTime rejectedAt;
}