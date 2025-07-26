package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.interfaces.IActivateUserUseCase;
import com.imaee.propinq.users.utils.EmailBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.imaee.propinq.users.utils.Constants.WELCOME_EMAIL_SUBJECT;

@Component
@AllArgsConstructor
public class ActivateUserUseCase implements IActivateUserUseCase {

    private final ITokenService tokenService;
    private final IUserRepository userRepository;
    private final IEmailService emailService;
    private final EmailBuilder emailBuilder;

    @Override
    public void activateUser(UUID userId, UUID activationTokenId) {
        tokenService.throwExceptionIfTokenIsExpired(activationTokenId);
        final var user = tokenService.findUserByTokenId(activationTokenId);
        activateUser(user);
        sendWelcomeEmail(user);
    }

    private void activateUser(User user) {
        user.setActivated(true);
        userRepository.save(user);
    }

    private void sendWelcomeEmail(User user) {
        final var emailBody = emailBuilder.buildWelcomeEmail(user);
        emailService.sendEmail(user.getEmail(), WELCOME_EMAIL_SUBJECT, emailBody);
    }
}