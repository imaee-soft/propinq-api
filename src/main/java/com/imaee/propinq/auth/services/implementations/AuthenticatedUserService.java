package com.imaee.propinq.auth.services.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
        return extractEmail()
                .flatMap(userRepository::findByEmailAndDeletedIsFalse);
    }

    private Optional<String> extractEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return Optional.ofNullable(userDetails.getUsername());
        }
        if (principal instanceof String email) {
            return Optional.of(email).filter(value -> !value.isBlank());
        }
        return Optional.empty();
    }
}