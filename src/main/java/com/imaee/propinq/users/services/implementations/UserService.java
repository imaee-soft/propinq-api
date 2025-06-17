package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.shared.data.repositories.IUserRepository;
import com.imaee.propinq.users.controllers.requests.ActivateUserRequest;
import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import com.imaee.propinq.users.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ITokenService tokenService;

    public UserService(IUserRepository userRepository, ITokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void activateUser(UUID userId, UUID activationTokenId) {
        throwExceptionIfTokenIsExpired(activationTokenId);

        User user = findUserToActivateOrThrowException(activationTokenId);

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
    private User findUserToActivateOrThrowException(UUID activationTokenId) {
        User user = tokenService.findUserByTokenId();
        ifUserIsActivatedThrowException(user);
        return User;
    }
}
