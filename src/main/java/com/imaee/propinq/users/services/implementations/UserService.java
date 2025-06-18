package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.shared.data.repositories.IUserRepository;
import com.imaee.propinq.users.controllers.requests.ActivateUserRequest;
import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.interfaces.IUserService;
import com.imaee.propinq.users.utils.EmailBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ITokenService tokenService;
    private final EmailBuilder emailBuilder;

    public UserService(IUserRepository userRepository, ITokenService tokenService, EmailBuilder emailBuilder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.emailBuilder = emailBuilder;
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
        String emailBody = emailBuilder.buildWelcomeEmail(user)
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
