package com.imaee.propinq.users.data.repositories;

import com.imaee.propinq.users.data.models.ProfileChange;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IProfileChangeRepository extends JpaRepository<ProfileChange, UUID> {
    Optional<ProfileChange> findByUser(User user);
}