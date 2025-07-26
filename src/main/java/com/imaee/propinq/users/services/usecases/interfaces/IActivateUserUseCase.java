package com.imaee.propinq.users.services.usecases.interfaces;

import java.util.UUID;

public interface IActivateUserUseCase {
    void activateUser(UUID userId, UUID activationTokenId);
}