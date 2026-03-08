package com.imaee.propinq.contacts.services.usecases.implementations;

import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.contacts.data.repositories.IContactRepository;
import com.imaee.propinq.contacts.mappers.ContactMapper;
import com.imaee.propinq.contacts.services.usecases.interfaces.IGetContactDetailsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.contacts.Constants.CONTACT_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class GetContactDetailsUseCase implements IGetContactDetailsUseCase {

    private final IContactRepository contactRepository;

    @Override
    public ContactDetailResponse getContactDetails(UUID contactId) {
        final var loggedUserEmail = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        final var contact = getContactOrThrowException(contactId, loggedUserEmail);
        final var isOwnerRetrieving = contact.getProperty().getUser()
                .getEmail().equals(loggedUserEmail);
        return ContactMapper.buildContactDetailResponse(contact, true, isOwnerRetrieving);
    }

    private Contact getContactOrThrowException(UUID contactId, String userEmail) {
        return contactRepository.findById(contactId)
                .filter(contact ->
                        contact.getIssuer().getEmail().equals(userEmail) ||
                        contact.getProperty().getUser().getEmail().equals(userEmail)
                )
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, CONTACT_NOT_FOUND));
    }
}