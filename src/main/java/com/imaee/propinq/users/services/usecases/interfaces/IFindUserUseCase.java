package com.imaee.propinq.users.services.usecases.interfaces;

import com.imaee.propinq.users.data.models.User;

import java.util.UUID;

public interface IFindUserUseCase {
    User findUserById(UUID userId);
    User findUserByEmail(String email);
}