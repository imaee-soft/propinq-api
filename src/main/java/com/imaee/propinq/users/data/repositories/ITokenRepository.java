package com.imaee.propinq.users.data.repositories;

import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

import static com.imaee.propinq.users.utils.TokenQueries.ACTIVE_TOKEN_BY_USER_AND_EXPIRATION;

public interface ITokenRepository extends JpaRepository<Token, UUID> {
    @Query(ACTIVE_TOKEN_BY_USER_AND_EXPIRATION)
    Optional<Token> findActiveTokenByUser(User user);
}