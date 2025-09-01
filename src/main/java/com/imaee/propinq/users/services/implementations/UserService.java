package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;
import com.imaee.propinq.users.controllers.requests.UpdateUserRequest;
import com.imaee.propinq.users.controllers.responses.UserResponse;

import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.interfaces.IUserService;
import com.imaee.propinq.users.services.usecases.interfaces.IFindUserUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IRecoverPasswordUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.ISaveUserUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IUpdateUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.imaee.propinq.users.mappers.UserMapper.toUserResponse;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final ISaveUserUseCase saveUserUseCase;
    private final IFindUserUseCase findUserUseCase;
    private final IRecoverPasswordUseCase recoverPasswordUseCase;
    private final IUpdateUserUseCase updateUserUseCase;
    private final UserActivationService userActivationService;

    @Override
    public User findUserById(UUID userId) {
        return findUserUseCase.findUserById(userId);
    }

    @Override
    public User findUserByEmail(String email) {
        return findUserUseCase.findUserByEmail(email);
    }

    @Override
    public void saveUser(SignUpRequest createUserRequest) {
        saveUserUseCase.saveUser(createUserRequest);
    }

    @Override
    public void sendEmailToRecoverPassword(String email) {
        recoverPasswordUseCase.sendEmailToRecoverPassword(email);
    }

    @Override
    public void recoverPassword(RecoverPasswordRequest recoverPasswordRequest) {
        recoverPasswordUseCase.recoverPassword(recoverPasswordRequest);
    }

    @Override
    public void updateUser(UUID userId, UpdateUserRequest updateUserRequest) {
        updateUserUseCase.updateUser(userId, updateUserRequest);
    }

    @Override
    public UserResponse getUser(UUID userId) {
        return toUserResponse(findUserUseCase.findUserById(userId));
    }
}