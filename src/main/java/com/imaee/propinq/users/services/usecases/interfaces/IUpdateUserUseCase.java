package com.imaee.propinq.users.services.usecases.interfaces;

import com.imaee.propinq.users.controllers.requests.UpdateUserRequest;

import java.util.UUID;

public interface IUpdateUserUseCase {
    void updateUser(UUID userId, UpdateUserRequest updateUserRequest);
}