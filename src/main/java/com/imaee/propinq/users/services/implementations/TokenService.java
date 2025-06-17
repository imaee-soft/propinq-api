package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.repositories.ITokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class TokenService {

    private final ITokenRepository tokenRepository;
    public TokenService(ITokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token findTokenByIdOrThrowException(UUID tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token with UUID " + tokenId + " does not exist."));
    }
}
