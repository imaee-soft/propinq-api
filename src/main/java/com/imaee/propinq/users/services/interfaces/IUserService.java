package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.controllers.requests.ActivateUserRequest;

import java.util.UUID;

public interface IUserService {

    void activateUser(UUID userId, ActivateUserRequest activateUserRequest);
}
