package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.shared.services.interfaces.IEmailService;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.usecases.interfaces.ISaveUserUseCase;
import com.imaee.propinq.users.utils.EmailBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.users.Constants.EXISTENT_EMAIL;
import static com.imaee.propinq.users.mappers.UserMapper.toUser;
import static com.imaee.propinq.users.utils.Constants.ACTIVATION_EMAIL_SUBJECT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class SaveUserUseCase implements ISaveUserUseCase {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ITokenService tokenService;
    private final IEmailService emailService;
    private final EmailBuilder emailBuilder;

    @Override
    public void saveUser(SignUpRequest createUserRequest) {
        throwExceptionIfUserExistsByEmail(createUserRequest.email());
        final var user = createUser(createUserRequest);
        userRepository.save(user);
        sendActivationEmail(user, tokenService.saveToken(user).getTokenId());
    }

    public void throwExceptionIfUserExistsByEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new ResponseStatusException(BAD_REQUEST, EXISTENT_EMAIL);
    }

    private User createUser(SignUpRequest createUserRequest) {
        final var encodedPassword = passwordEncoder.encode(createUserRequest.password());
        return toUser(createUserRequest, encodedPassword);
    }

    private void sendActivationEmail(User user, UUID activationTokenId) {
        emailService.sendEmail(
                user.getEmail(),
                ACTIVATION_EMAIL_SUBJECT,
                emailBuilder.buildActivationEmailBody(user, activationTokenId)
        );
    }
}