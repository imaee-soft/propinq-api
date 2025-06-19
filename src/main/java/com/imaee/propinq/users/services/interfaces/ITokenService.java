package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;

import java.util.UUID;

public interface ITokenService {

    Token findTokenByIdOrThrowException(UUID tokenId);

    User findUserByTokenId(UUID tokenId);
}
