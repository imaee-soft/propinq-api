package com.imaee.propinq.shared.data.repositories;

import com.imaee.propinq.users.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
