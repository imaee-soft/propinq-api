package com.imaee.propinq.users.services.usecases.implementations;

import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.usecases.interfaces.IFindUserUseCase;
import com.imaee.propinq.users.services.usecases.interfaces.IGetLoggedUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetLoggedUserUseCase implements IGetLoggedUserUseCase {

    private final IFindUserUseCase findUserUseCase;

    @Override
    public User getLoggedUser() {
        final var authenticatedUserEmail = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return findUserUseCase.findUserByEmail(authenticatedUserEmail);
    }
}