package com.imaee.propinq.auth.services.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@AllArgsConstructor
public class AuthenticatedUserService implements IAuthenticatedUserService {

    private final IUserRepository userRepository;

    @Override
    public User safelyGetLoggedUser() {
        return getOptionalUser().orElse(null);
    }

    @Override
    public User getLoggedUserOrThrowException() {
        return getOptionalUser()
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED));
    }

    private Optional<User> getOptionalUser() {
        return ofNullable((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .flatMap(userRepository::findByEmailAndDeletedIsFalse);
    }
}