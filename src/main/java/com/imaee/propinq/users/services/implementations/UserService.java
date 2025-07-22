package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.exceptions.custom_exceptions.DuplicateEmailException;
import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;
import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.interfaces.IEmailService;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.interfaces.IUserService;
import com.imaee.propinq.users.utils.EmailBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.users.Constants.USER_NOT_FOUND;
import static com.imaee.propinq.users.mappers.UserMapper.toUser;
import static com.imaee.propinq.users.utils.Constants.ACTIVATION_EMAIL_SUBJECT;
import static com.imaee.propinq.users.utils.Constants.EXPIRED_ACTIVATION_TOKEN_MESSAGE;
import static com.imaee.propinq.users.utils.Constants.NEW_ACTIVATION_TOKEN_EMAIL_SUBJECT;
import static com.imaee.propinq.users.utils.Constants.NONEXISTING_USER_EMAIL_MESSAGE;
import static com.imaee.propinq.users.utils.Constants.PASSWORDS_DO_NOT_MATCH_MESSAGE;
import static com.imaee.propinq.users.utils.Constants.RECOVER_PASSWORD_EMAIL_SUBJECT;
import static com.imaee.propinq.users.utils.Constants.TOKEN_NOT_EXPIRED_MESSAGE;
import static com.imaee.propinq.users.utils.Constants.USER_ALREADY_ACTIVATED_MESSAGE;
import static com.imaee.propinq.users.utils.Constants.WELCOME_EMAIL_SUBJECT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ITokenService tokenService;
    private final EmailBuilder emailBuilder;
    private final PasswordEncoder passwordEncoder;
    private final IEmailService emailService;

    @Override
    public User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, USER_NOT_FOUND));
    }

    @Override
    public void saveUser(SignUpRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.email()))
            throw new DuplicateEmailException("Email already exists: " + createUserRequest.email());

        final var user = createUser(createUserRequest);
        userRepository.save(user);
        sendActivationEmail(user, generateActivationToken(user));
    }

    private User createUser(SignUpRequest createUserRequest) {
        final var encodedPassword = passwordEncoder.encode(createUserRequest.password());
        return toUser(createUserRequest, encodedPassword);
    }

    private UUID generateActivationToken(User user) {
        return tokenService.saveToken(user).getTokenId();
    }

    private void sendActivationEmail(User user, UUID activationTokenId) {
        emailService.sendEmail(
                user.getEmail(),
                ACTIVATION_EMAIL_SUBJECT,
                emailBuilder.buildActivationEmailBody(user, activationTokenId)
        );
    }

    @Override
    public void activateUser(UUID userId, UUID activationTokenId) {
        throwExceptionIfTokenIsExpired(activationTokenId);
        final var user = findUserToActivateOrThrowException(activationTokenId);
        user.setActivated(true);
        userRepository.save(user);
        sendWelcomeEmail(user);
    }

    private void sendWelcomeEmail(User user) {
        String emailBody = emailBuilder.buildWelcomeEmail(user);
        emailService.sendEmail(user.getEmail(), WELCOME_EMAIL_SUBJECT, emailBody);
    }

    private void throwExceptionIfTokenIsExpired(UUID tokenId) {
        if (isTokenExpired(tokenId))
            throw new ResponseStatusException(BAD_REQUEST, EXPIRED_ACTIVATION_TOKEN_MESSAGE);
    }

    private boolean isTokenExpired(UUID tokenId) {
        return tokenService.findTokenByIdOrThrowException(tokenId).isExpired();
    }

    private User findUserToActivateOrThrowException(UUID tokenId) {
        final var user = tokenService.findUserByTokenId(tokenId);
        ifUserIsActivatedThrowException(user);
        return user;
    }

    private void ifUserIsActivatedThrowException(User user) {
        if (user.isActivated())
            throw new IllegalArgumentException(USER_ALREADY_ACTIVATED_MESSAGE);
    }

    @Override
    public void sendEmailToRecoverPassword(String email) {
        final var user = findUserByEmailOrThrowException(email);
        final var recoverPasswordToken = tokenService.saveToken(user);
        final var emailContent = emailBuilder.buildRecoverPasswordEmail(user.getFirstName(), recoverPasswordToken.getTokenId());
        emailService.sendEmail(email, RECOVER_PASSWORD_EMAIL_SUBJECT, emailContent);
    }

    private User findUserByEmailOrThrowException(String email) {
        return userRepository.findByEmailAndDeletedIsFalse(email)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, NONEXISTING_USER_EMAIL_MESSAGE + email));
    }

    @Override
    public void recoverPassword(RecoverPasswordRequest recoverPasswordRequest) {
        throwExceptionIfPasswordDontMatch(recoverPasswordRequest.password(), recoverPasswordRequest.confirmPassword());
        throwExceptionIfTokenIsExpired(recoverPasswordRequest.recoverPasswordToken());
        final var user = tokenService.findUserByTokenId(recoverPasswordRequest.recoverPasswordToken());
        user.setPassword(passwordEncoder.encode(recoverPasswordRequest.password()));
        userRepository.save(user);
    }

    public void throwExceptionIfPasswordDontMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword))
            throw new ResponseStatusException(BAD_REQUEST, PASSWORDS_DO_NOT_MATCH_MESSAGE);
    }

    @Override
    public void resendActivationEmail(SendEmailRequest sendEmailRequest) {
        User user = findUserByEmailOrThrowException(sendEmailRequest.email());
        ifUserIsActivatedThrowException(user);
        Token token = tokenService.findActiveTokenByUser(user);
        sendNewActivationEmail(user,token.getTokenId());
    }

    private void sendNewActivationEmail(User user, UUID activationTokenId) {
        String emailBody = emailBuilder.buildActivationEmailBody(user, activationTokenId);
        emailService.sendEmail(user.getEmail(), NEW_ACTIVATION_TOKEN_EMAIL_SUBJECT, emailBody);
    }

    @Override
    public void sendNewActivationToken(SendNewActivationTokenRequest sendNewActivationTokenRequest) {
        throwExceptionIfTokenIsNotExpired(sendNewActivationTokenRequest.activationToken());
        User user = tokenService.findUserByTokenId(sendNewActivationTokenRequest.activationToken());
        Token token = tokenService.saveToken(user);
        sendNewActivationEmail(user,token.getTokenId());
    }

    private void throwExceptionIfTokenIsNotExpired(UUID activationTokenId) {
        if (!isTokenExpired(activationTokenId))
            throw new ResponseStatusException(BAD_REQUEST, TOKEN_NOT_EXPIRED_MESSAGE);
    }

}
