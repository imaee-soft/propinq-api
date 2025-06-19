package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.ITokenRepository;
import com.imaee.propinq.users.services.interfaces.ITokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    private final ITokenRepository tokenRepository;
    public TokenService(ITokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User findUserByTokenId(UUID tokenId) {
        return findTokenByIdOrThrowException(tokenId).getUser();
    }

    @Override
    public Token findTokenByIdOrThrowException(UUID tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token with UUID " + tokenId + " does not exist."));
    }


}
