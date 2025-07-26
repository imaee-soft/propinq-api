package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;

import java.util.UUID;

public interface ITokenService {
    Token findTokenById(UUID tokenId);
    User findUserByTokenId(UUID tokenId);
    Token saveToken(User user);
    Token findActiveTokenByUser(User user);
    boolean isTokenExpired(UUID tokenId);
    void throwExceptionIfTokenIsExpired(UUID tokenId);
}
