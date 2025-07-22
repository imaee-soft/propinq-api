package com.imaee.propinq.auth.controllers.responses;

import com.imaee.propinq.users.data.enums.Role;

import java.util.UUID;

public record UserAuthResponse(
        UUID userId,
        String username,
        Role role
) {}