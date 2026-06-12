package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.shared.services.interfaces.IEmailService;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.interfaces.IActivateUserUseCase;
import com.imaee.propinq.users.utils.EmailBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
    public void activateUser(String email, String verificationCode) {
        final var user = userRepository.findByEmailAndDeletedIsFalse(email)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Usuario no encontrado"));
        
        final var token = tokenService.findActiveTokenByUser(user);
        
        if (!token.getVerificationCode().equals(verificationCode)) {
            throw new ResponseStatusException(BAD_REQUEST, "Código de verificación inválido o expirado");
        }
        
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