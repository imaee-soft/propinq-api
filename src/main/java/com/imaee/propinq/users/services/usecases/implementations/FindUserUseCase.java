package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import com.imaee.propinq.users.services.usecases.interfaces.IFindUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.users.Constants.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
public class FindUserUseCase implements IFindUserUseCase {

    private final IUserRepository userRepository;

    @Override
    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, USER_NOT_FOUND));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndDeletedIsFalse(email)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, USER_NOT_FOUND));
    }
}