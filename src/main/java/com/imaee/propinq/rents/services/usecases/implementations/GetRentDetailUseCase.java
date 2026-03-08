package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.rents.controllers.responses.RentDetail;
import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.rents.mappers.RentMapper;
import com.imaee.propinq.rents.services.usecases.interfaces.IFindRentByIdUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.IGetRentDetailUseCase;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@AllArgsConstructor
public class GetRentDetailUseCase implements IGetRentDetailUseCase {

    private final IFindRentByIdUseCase findRentByIdUseCase;
    private final IAuthenticatedUserService authenticatedUserService;

    @Override
    public RentDetail getRent(UUID rentId) {
        final var rent = findRentByIdUseCase.findById(rentId);
        final var user = authenticatedUserService.getLoggedUserOrThrowException();
        throwExceptionIfIsNotOwnerOrTenant(rent, user);
        return RentMapper.buildRentDetail(rent, user);
    }

    private void throwExceptionIfIsNotOwnerOrTenant(Rent rent, User user) throws ResponseStatusException {
        final var contact = rent.getContact();
        if (!(
                user.getUserId().equals(contact.getIssuer().getUserId())
                        || user.getUserId().equals(contact.getProperty().getUser().getUserId())
        )) throw new ResponseStatusException(FORBIDDEN);
    }
}