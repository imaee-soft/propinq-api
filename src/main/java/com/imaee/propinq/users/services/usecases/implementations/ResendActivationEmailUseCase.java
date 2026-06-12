package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.IActivationEmailSender;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.interfaces.IFindUserUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IResendActivationEmailUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.imaee.propinq.users.utils.Constants.USER_ALREADY_ACTIVATED_MESSAGE;

@Component
@AllArgsConstructor
public class ResendActivationEmailUseCase implements IResendActivationEmailUseCase {

    private final IFindUserUseCase findUserUseCase;
    private final ITokenService tokenService;
    private final IActivationEmailSender activationEmailSender;

    @Override
    public void resendActivationEmail(SendEmailRequest sendEmailRequest) {
        final var user = findUserUseCase.findUserByEmail(sendEmailRequest.email());
        throwExceptionIfUserIsAlreadyActivated(user);
        resendActivationEmail(user);
    }

    private void throwExceptionIfUserIsAlreadyActivated(User user) {
        if (user.isActivated())
            throw new IllegalArgumentException(USER_ALREADY_ACTIVATED_MESSAGE);
    }

    private void resendActivationEmail(User user) {
        activationEmailSender.sendActivationEmail(
            user,
            tokenService.findActiveTokenByUser(user).getVerificationCode()
        );
    }
}