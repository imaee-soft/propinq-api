package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.ITokenRepository;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.users.utils.Constants.EXPIRED_ACTIVATION_TOKEN_MESSAGE;
import static com.imaee.propinq.users.utils.Constants.NONEXISTING_TOKEN_MESSAGE;

@Service
@AllArgsConstructor
public class TokenService implements ITokenService {

    private final ITokenRepository tokenRepository;

    @Override
    public User findUserByTokenId(UUID tokenId) {
        return findTokenByIdOrThrowException(tokenId).getUser();
    }

    @Override
    public Token findTokenByIdOrThrowException(UUID tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, NONEXISTING_TOKEN_MESSAGE));
    }

    @Override
    public Token saveToken(User user) {
        Token token = Token.builder()
                .user(user)
                .build();
        return tokenRepository.save(token);
    }

    @Override
    public Token findActiveTokenByUser(User user) {
        return tokenRepository.findActiveTokenByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, EXPIRED_ACTIVATION_TOKEN_MESSAGE));
    }
}
