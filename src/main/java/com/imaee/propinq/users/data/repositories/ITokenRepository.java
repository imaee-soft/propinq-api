package com.imaee.propinq.users.data.repositories;

import com.imaee.propinq.users.data.models.Token;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ITokenRepository extends JpaRepository<Token, UUID> {
    @Query("SELECT t FROM tokens t " +
            "WHERE t.user = ?1 " +
            "AND t.tokenExpirationDate > CURRENT_TIMESTAMP ")
    Optional<Token> findActiveTokenByUser(User user);
}
