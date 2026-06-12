package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;

public interface IUserActivationService {
    void activateUser(String email, String verificationCode);
    void resendActivationEmail(SendEmailRequest sendEmailRequest);
    void sendNewActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest);
}
