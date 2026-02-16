package com.imaee.propinq.users.data.repositories;

import com.imaee.propinq.users.data.enums.Role;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Optional<User> findByEmailAndDeletedIsFalse(String email);
    List<User> findAllByRole(Role role);
}