package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;
import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.data.models.User;

import java.util.UUID;

public interface IUserService {

    User findUserById(UUID id);

    void saveUser(SignUpRequest createUserRequest);

    void activateUser(UUID userId, UUID activationTokenId);

    void sendEmailToRecoverPassword(String email);

    void recoverPassword(RecoverPasswordRequest recoverPasswordRequest);

    void resendActivationEmail(SendEmailRequest sendEmailRequest);

    void sendNewActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest);

}