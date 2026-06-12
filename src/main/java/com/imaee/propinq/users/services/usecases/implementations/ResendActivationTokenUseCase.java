package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.IActivationEmailSender;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.interfaces.IResendActivationTokenUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.users.utils.Constants.TOKEN_NOT_EXPIRED_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class ResendActivationTokenUseCase implements IResendActivationTokenUseCase {

    private final ITokenService tokenService;
    private final IActivationEmailSender activationEmailSender;

    @Override
    public void resendActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest) {
        throwExceptionIfTokenIsNotExpired(sendNewActivationTokenRequest.activationToken());
        final var user = tokenService.findUserByTokenId(sendNewActivationTokenRequest.activationToken());
        final var token = tokenService.saveToken(user);
        activationEmailSender.sendNewActivationEmail(user, token.getVerificationCode());
    }

    private void throwExceptionIfTokenIsNotExpired(UUID activationTokenId) {
        if (!tokenService.isTokenExpired(activationTokenId))
            throw new ResponseStatusException(BAD_REQUEST, TOKEN_NOT_EXPIRED_MESSAGE);
    }
}