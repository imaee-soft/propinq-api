package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.rents.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.rents.data.enums.ContactState;
import com.imaee.propinq.rents.data.models.Contact;
import com.imaee.propinq.rents.data.repositories.IContactRepository;
import com.imaee.propinq.rents.mappers.ContactMapper;
import com.imaee.propinq.rents.services.usecases.interfaces.IGetOwnerContactsUseCase;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import static com.imaee.propinq.rents.data.enums.ContactState.getContactState;

@Component
@AllArgsConstructor
public class GetOwnerContactsUseCase implements IGetOwnerContactsUseCase {

    private final IAuthenticatedUserService authenticatedUserService;
    private final IContactRepository contactRepository;

    @Override
    public Page<ContactDetailResponse> getOwnerContacts(Integer pageNumber, Integer pageSize, String status) {
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        final var contacts = (status == null || "all".equals(status))
                ? getAllOwnerContacts(loggedUser, pageNumber, pageSize)
                : getAllOwnerContactsByStatus(loggedUser, pageNumber, pageSize, status);
        return contacts.map(ContactMapper::buildContactDetailResponse);
    }

    private Page<Contact> getAllOwnerContacts(User user, Integer pageNumber, Integer pageSize) {
        return contactRepository.findAllByProperty_User(
                user,
                PageRequest.of(pageNumber, pageSize)
        );
    }

    private Page<Contact> getAllOwnerContactsByStatus(
            User user,
            Integer pageNumber,
            Integer pageSize,
            String status
    ) {
        return contactRepository.findAllByProperty_UserAndState(
                user,
                getContactState(status.toUpperCase()),
                PageRequest.of(pageNumber, pageSize)
        );
    }
}