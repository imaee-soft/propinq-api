package com.imaee.propinq.users.controllers.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ActivateUserRequest(
        @NotNull(message = "Activation token must not be null")
        UUID activationToken
) {
}
