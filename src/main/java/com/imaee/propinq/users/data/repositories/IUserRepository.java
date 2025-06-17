package com.imaee.propinq.users.data.repositories;

import com.imaee.propinq.users.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
}
