package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.controllers.requests.SignUpRequest;

import java.util.UUID;

public interface IUserService {

    void saveUser(SignUpRequest createUserRequest);

    void activateUser(UUID userId, UUID activationTokenId);
}
