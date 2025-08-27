package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.interfaces.IFindUserUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IResendActivationEmailUseCase;
import com.imaee.propinq.users.utils.EmailBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.imaee.propinq.users.utils.Constants.NEW_ACTIVATION_TOKEN_EMAIL_SUBJECT;
import static com.imaee.propinq.users.utils.Constants.USER_ALREADY_ACTIVATED_MESSAGE;

@Component
@AllArgsConstructor
public class ResendActivationEmailUseCase implements IResendActivationEmailUseCase {

    private final IFindUserUseCase findUserUseCase;
    private final ITokenService tokenService;
    private final IEmailService emailService;
    private final EmailBuilder emailBuilder;

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
        sendNewActivationEmail(
                user,
                tokenService.findActiveTokenByUser(user).getTokenId()
        );
    }

    private void sendNewActivationEmail(User user, UUID activationTokenId) {
        emailService.sendEmail(
                user.getEmail(),
                NEW_ACTIVATION_TOKEN_EMAIL_SUBJECT,
                emailBuilder.buildActivationEmailBody(user, activationTokenId)
        );
    }
}