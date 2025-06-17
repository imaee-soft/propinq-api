package com.imaee.propinq.users.data.repositories;

import com.imaee.propinq.users.data.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITokenRepository extends JpaRepository<Token, UUID> {
}
