package com.imaee.propinq.users.services.usecases.interfaces;

import com.imaee.propinq.users.controllers.requests.SendEmailRequest;

public interface IResendActivationEmailUseCase {
    void resendActivationEmail(SendEmailRequest sendEmailRequest);
}
