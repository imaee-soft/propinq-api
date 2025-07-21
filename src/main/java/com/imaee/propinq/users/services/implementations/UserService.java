package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.exceptions.custom_exceptions.DuplicateEmailException;
import com.imaee.propinq.exceptions.custom_exceptions.DuplicateUserNameException;
import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.controllers.requests.SendEmailRequest;
import com.imaee.propinq.users.controllers.requests.SendNewActivationTokenRequest;
import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.mappers.UserMapper;
import com.imaee.propinq.users.services.interfaces.IEmailService;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.interfaces.IUserService;
import com.imaee.propinq.users.utils.EmailBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.imaee.propinq.users.Constants.USER_NOT_FOUND;
import static com.imaee.propinq.users.utils.Constants.*;
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

        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new DuplicateEmailException("Email already exists: " + createUserRequest.email());
        }
        
        User newUser = createUser(createUserRequest);
        userRepository.save(newUser);
        
        UUID activationTokenId = generateActivationToken(newUser);
        sendActivationEmail(newUser, activationTokenId);
    }

    private User createUser(SignUpRequest createUserRequest) {
        User user = UserMapper.toUser(createUserRequest);
        String passwordEncoded = passwordEncoder.encode(createUserRequest.password());
        user.setPassword(passwordEncoded);
        return user;
    }

    private UUID generateActivationToken(User user) {
        return tokenService.saveToken(user).getTokenId();
    }

    private void sendActivationEmail(User user, UUID activationTokenId) {

        String emailBody = emailBuilder.buildActivationEmailBody(user,activationTokenId);

        emailService.sendEmail(user.getEmail(),ACTIVATION_EMAIL_SUBJECT, emailBody);
    }

    @Override
    public void activateUser(UUID userId, UUID activationTokenId) {
        throwExceptionIfTokenIsExpired(activationTokenId);

        User user = findUserToActivateOrThrowException(activationTokenId);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EXPIRED_ACTIVATION_TOKEN_MESSAGE);
    }

    private boolean isTokenExpired(UUID tokenId) {
        Token activationToken = findActivationTokenByIdOrThrowException(tokenId);
        return activationToken.getTokenExpirationDate().isBefore(LocalDateTime.now());
    }

    private Token findActivationTokenByIdOrThrowException(UUID tokenId) {
        return tokenService.findTokenByIdOrThrowException(tokenId);
    }

    private User findUserToActivateOrThrowException(UUID tokenId) {
        User user = tokenService.findUserByTokenId(tokenId);
        ifUserIsActivatedThrowException(user);
        return user;
    }

    private void ifUserIsActivatedThrowException(User user) {
        if (user.getActivated())
            throw new IllegalArgumentException(USER_ALREADY_ACTIVATED_MESSAGE);
    }

    @Override
    public void sendEmailToRecoverPassword(String email) {
        User user = findUserByEmailOrThrowException(email);
        Token recoverPasswordToken = tokenService.saveToken(user);
        String emailContent = emailBuilder.buildRecoverPasswordEmail(user.getFirstName(), recoverPasswordToken.getTokenId());

        emailService.sendEmail(email, RECOVER_PASSWORD_EMAIL_SUBJECT, emailContent);
    }

    private User findUserByEmailOrThrowException(String email) {
        return userRepository.findByEmailAndDeletedIsFalse(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, NONEXISTING_USER_EMAIL_MESSAGE + email));
    }

    @Override
    public void recoverPassword(RecoverPasswordRequest recoverPasswordRequest) {
        throwExceptionIfPasswordDontMatch(recoverPasswordRequest.password(), recoverPasswordRequest.confirmPassword());
        throwExceptionIfTokenIsExpired(recoverPasswordRequest.recoverPasswordToken());
        User user = tokenService.findUserByTokenId(recoverPasswordRequest.recoverPasswordToken());
        user.setPassword(passwordEncoder.encode(recoverPasswordRequest.password()));

        userRepository.save(user);
    }

    public void throwExceptionIfPasswordDontMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PASSWORDS_DO_NOT_MATCH_MESSAGE);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TOKEN_NOT_EXPIRED_MESSAGE);
    }

}
