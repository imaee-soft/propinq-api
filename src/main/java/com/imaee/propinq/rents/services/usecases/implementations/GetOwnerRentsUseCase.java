package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import com.imaee.propinq.rents.data.repositories.IRentRepository;
import com.imaee.propinq.rents.mappers.RentMapper;
import com.imaee.propinq.rents.services.usecases.interfaces.IGetOwnerRentsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetOwnerRentsUseCase implements IGetOwnerRentsUseCase {

    private final IAuthenticatedUserService authenticatedUserService;
    private final IRentRepository rentRepository;

    @Override
    public Page<SimpleRent> getOwnerRents(Integer pageNumber, Integer pageSize, String surname, String status) {
        final var user = authenticatedUserService.getLoggedUserOrThrowException();
        return rentRepository.findByOwnerAndOptionalTenantSurname(
                user,
                surname.isEmpty() ? null : surname,
                "all".equals(status) ? null : status,
                PageRequest.of(pageNumber, pageSize)
        ).map(RentMapper::buildSimpleRent);
    }
}