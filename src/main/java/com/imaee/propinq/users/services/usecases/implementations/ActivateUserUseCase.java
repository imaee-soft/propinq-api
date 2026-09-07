package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.interfaces.IActivateUserUseCase;
import com.imaee.propinq.users.utils.EmailBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.users.utils.Constants.TOKEN_USER_MISMATCH_MESSAGE;
import static com.imaee.propinq.users.utils.Constants.WELCOME_EMAIL_SUBJECT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class ActivateUserUseCase implements IActivateUserUseCase {

    private final ITokenService tokenService;
    private final IUserRepository userRepository;
    private final IEmailService emailService;
    private final EmailBuilder emailBuilder;

    @Override
    @Transactional
    public void activateUser(UUID userId, UUID activationTokenId) {
        final var user = tokenService.findUserByTokenId(activationTokenId);
        throwExceptionIfTokenDoesNotBelongToUser(user, userId);

        if (user.isActivated()) {
            return;
        }

        tokenService.throwExceptionIfTokenIsExpired(activationTokenId);
        user.setActivated(true);
        userRepository.save(user);
        tokenService.expireToken(activationTokenId);
        sendWelcomeEmail(user);
    }

    private void throwExceptionIfTokenDoesNotBelongToUser(User user, UUID userId) {
        if (!user.getUserId().equals(userId)) {
            throw new ResponseStatusException(BAD_REQUEST, TOKEN_USER_MISMATCH_MESSAGE);
        }
    }

    private void sendWelcomeEmail(User user) {
        final var emailBody = emailBuilder.buildWelcomeEmail(user);
        emailService.sendEmail(user.getEmail(), WELCOME_EMAIL_SUBJECT, emailBody);
    }
}
