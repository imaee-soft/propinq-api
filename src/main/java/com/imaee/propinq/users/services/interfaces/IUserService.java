package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.controllers.requests.UpdateUserRequest;
import com.imaee.propinq.users.controllers.responses.UserResponse;
import com.imaee.propinq.users.data.models.User;

import java.util.UUID;

public interface IUserService {

    User findUserById(UUID userId);

    User findUserByEmail(String email);

    void saveUser(SignUpRequest createUserRequest);

    void sendEmailToRecoverPassword(String email);

    void recoverPassword(RecoverPasswordRequest recoverPasswordRequest);

    void updateUser(UUID userId, UpdateUserRequest updateUserRequest);

    UserResponse getUser(UUID userId);
}