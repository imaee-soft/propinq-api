package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.shared.data.repositories.IUserRepository;
import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.mappers.UserMapper;
import com.imaee.propinq.users.services.interfaces.IEmailService;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.interfaces.IUserService;
import com.imaee.propinq.users.utils.EmailBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ITokenService tokenService;
    private final EmailBuilder emailBuilder;
    private final PasswordEncoder passwordEncoder;
    private final IEmailService emailService;

    public UserService(IUserRepository userRepository, ITokenService tokenService, EmailBuilder emailBuilder, PasswordEncoder passwordEncoder, IEmailService emailService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.emailBuilder = emailBuilder;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;

    }

    @Override
    public void saveUser(SignUpRequest createUserRequest) {
        ifEmailAlreadyExistsThrowException(createUserRequest.email());
        ifUsernameAlreadyExistsThrowException(createUserRequest.username());
        User newUser = createUser(createUserRequest);
        userRepository.save(newUser);
        UUID activationTokenId = generateActivationToken(newUser);
        sendActivationEmail(newUser, activationTokenId);
    }

    private void ifEmailAlreadyExistsThrowException(String email) {
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException( "Email already exists.");
        }
    }

    private void ifUsernameAlreadyExistsThrowException(String username) {
        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Username already exists.");
        }
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

    private void sendActivationEmail(User user, UUID activationTokenId){
        String emailBody = emailBuilder.buildActivationEmailBody(user,activationTokenId);
        emailService.sendEmail(user.getEmail(),"Account Activation", emailBody);
    }

    @Override
    public void activateUser(UUID userId, UUID activationTokenId) {
        throwExceptionIfTokenIsExpired(activationTokenId);

        User user = findUserToActivateOrThrowException(activationTokenId);
        user.setActivated(true);
        userRepository.save(user);

        sendWelcomeEmail(user);

    }
    private void sendWelcomeEmail(User user){
        String emailBody = emailBuilder.buildWelcomeEmail(user);
        emailService.sendEmail(user.getEmail(), "Welcome to PropInq", emailBody);
    }

    private void throwExceptionIfTokenIsExpired(UUID tokenId) {
        if(isTokenExpired(tokenId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Activation token has expired");
        }
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
        if(user.isActivated()){
            throw new IllegalArgumentException("User is already activated.");
        }
    }
}
