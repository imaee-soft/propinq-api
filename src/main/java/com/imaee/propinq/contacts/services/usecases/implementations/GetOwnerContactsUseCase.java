package com.imaee.propinq.contacts.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.contacts.data.repositories.IContactRepository;
import com.imaee.propinq.contacts.mappers.ContactMapper;
import com.imaee.propinq.contacts.services.usecases.interfaces.IGetOwnerContactsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetOwnerContactsUseCase implements IGetOwnerContactsUseCase {

    private final IAuthenticatedUserService authenticatedUserService;
    private final IContactRepository contactRepository;

    @Override
    public Page<ContactDetailResponse> getOwnerContacts(Integer pageNumber, Integer pageSize, String surname, String status) {
        final var user = authenticatedUserService.getLoggedUserOrThrowException();
        return contactRepository.findByOwnerAndOptionalTenantSurname(
                user,
                surname.isEmpty() ? null : surname,
                "all".equals(status) ? null : status,
                PageRequest.of(pageNumber, pageSize)
        ).map(ContactMapper::buildContactDetailResponse);
    }
}