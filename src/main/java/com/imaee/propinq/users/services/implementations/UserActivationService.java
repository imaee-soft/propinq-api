package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;
import com.imaee.propinq.users.services.interfaces.IUserActivationService;
import com.imaee.propinq.users.services.usecases.interfaces.IActivateUserUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IResendActivationEmailUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IResendActivationTokenUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserActivationService implements IUserActivationService {

    private final IActivateUserUseCase activateUserUseCase;
    private final IResendActivationEmailUseCase resendActivationEmailUseCase;
    private final IResendActivationTokenUseCase resendActivationTokenUseCase;

    @Override
    public void activateUser(String email, String verificationCode) {
        activateUserUseCase.activateUser(email, verificationCode);
    }

    @Override
    public void resendActivationEmail(SendEmailRequest sendEmailRequest) {
        resendActivationEmailUseCase.resendActivationEmail(sendEmailRequest);
    }

    @Override
    public void sendNewActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest) {
        resendActivationTokenUseCase.resendActivationToken(sendNewActivationTokenRequest);
    }
}