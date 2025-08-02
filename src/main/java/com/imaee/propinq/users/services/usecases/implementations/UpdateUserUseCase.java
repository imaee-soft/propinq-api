package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.users.controllers.requests.UpdateUserRequest;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.usecases.interfaces.IFindUserUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IUpdateUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.users.Constants.EXISTENT_DNI;

@Component
@AllArgsConstructor
public class UpdateUserUseCase implements IUpdateUserUseCase {

    private final IFindUserUseCase findUserUseCase;
    private final IUserRepository userRepository;

    @Override
    public void updateUser(UUID userId, UpdateUserRequest updateUserRequest) {
        final var user = findUserUseCase.findUserById(userId);
        throwExceptionIfUserExistsByDni(user.getDni(), updateUserRequest.dni());
        updateUser(user, updateUserRequest);
    }

    private void throwExceptionIfUserExistsByDni(String currentDni, String newDni) {
        if (!currentDni.equals(newDni) && userRepository.existsByDni(newDni))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EXISTENT_DNI);
    }

    private void updateUser(User user, UpdateUserRequest updateUserRequest) {
        updateUserInfo(user, updateUserRequest);
        userRepository.save(user);
    }

    private void updateUserInfo(User user, UpdateUserRequest updateUserRequest) {
        user.setDni(updateUserRequest.dni());
        user.setFirstName(updateUserRequest.firstName());
        user.setLastName(updateUserRequest.lastName());
        user.setAddress(updateUserRequest.address());
        user.setPhoneNumber(updateUserRequest.phoneNumber());
    }
}