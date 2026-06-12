package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.users.controllers.requests.UpdateUserRequest;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.usecases.interfaces.IFindUserUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IUpdateUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class UpdateUserUseCase implements IUpdateUserUseCase {

    private final IFindUserUseCase findUserUseCase;
    private final IUserRepository userRepository;

    @Override
    public void updateUser(UUID userId, UpdateUserRequest updateUserRequest) {
        final var user = findUserUseCase.findUserById(userId);
        updateUser(user, updateUserRequest);
    }

    private void updateUser(User user, UpdateUserRequest updateUserRequest) {
        updateUserInfo(user, updateUserRequest);
        userRepository.save(user);
    }

    private void updateUserInfo(User user, UpdateUserRequest updateUserRequest) {
        user.setFirstName(updateUserRequest.firstName());
        user.setLastName(updateUserRequest.lastName());
        user.setAddress(updateUserRequest.address());
        user.setPhoneNumber(updateUserRequest.phoneNumber());
    }
}