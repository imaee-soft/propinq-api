package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;

import java.util.UUID;

public interface IUserActivationService {
    void activateUser(UUID userId, UUID activationTokenId);
    void resendActivationEmail(SendEmailRequest sendEmailRequest);
    void sendNewActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest);
}
