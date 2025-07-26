package com.imaee.propinq.users.services.usecases.interfaces;

import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;

public interface IResendActivationTokenUseCase {
    void resendActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest);
}
