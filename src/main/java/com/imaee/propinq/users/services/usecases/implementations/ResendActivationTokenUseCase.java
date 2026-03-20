package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.interfaces.IResendActivationTokenUseCase;
import com.imaee.propinq.users.utils.EmailBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.users.utils.Constants.NEW_ACTIVATION_TOKEN_EMAIL_SUBJECT;
import static com.imaee.propinq.users.utils.Constants.TOKEN_NOT_EXPIRED_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class ResendActivationTokenUseCase implements IResendActivationTokenUseCase {

    private final ITokenService tokenService;
    private final IEmailService emailService;
    private final EmailBuilder emailBuilder;

    @Override
    public void resendActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest) {
        throwExceptionIfTokenIsNotExpired(sendNewActivationTokenRequest.activationToken());
        final var user = tokenService.findUserByTokenId(sendNewActivationTokenRequest.activationToken());
        final var token = tokenService.saveToken(user);
        sendNewActivationEmail(user, token.getTokenId());
    }

    private void throwExceptionIfTokenIsNotExpired(UUID activationTokenId) {
        if (!tokenService.isTokenExpired(activationTokenId))
            throw new ResponseStatusException(BAD_REQUEST, TOKEN_NOT_EXPIRED_MESSAGE);
    }

    private void sendNewActivationEmail(User user, UUID activationTokenId) {
        String emailBody = emailBuilder.buildActivationEmailBody(user, activationTokenId);
        emailService.sendEmail(user.getEmail(), NEW_ACTIVATION_TOKEN_EMAIL_SUBJECT, emailBody);
    }
}