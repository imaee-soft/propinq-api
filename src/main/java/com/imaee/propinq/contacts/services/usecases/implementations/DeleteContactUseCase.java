package com.imaee.propinq.contacts.services.usecases.implementations;

import com.imaee.propinq.notifications.data.repositories.INotificationRepository;
import com.imaee.propinq.contacts.data.repositories.IContactRepository;
import com.imaee.propinq.contacts.services.usecases.interfaces.IDeleteContactUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.contacts.Constants.CONTACT_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class DeleteContactUseCase implements IDeleteContactUseCase {

    private final INotificationRepository notificationRepository;
    private final IContactRepository contactRepository;

    @Override
    @Transactional
    public void deleteContactRequest(UUID contactId) {
        final var loggedUserEmail = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (!contactRepository.existsByContactIdAndIssuer_Email(contactId, loggedUserEmail))
            throw new ResponseStatusException(BAD_REQUEST, CONTACT_NOT_FOUND);
        deleteContactCascade(contactId);
    }

    private void deleteContactCascade(UUID contactId) {
        notificationRepository.deleteByContact_ContactId(contactId);
        contactRepository.deleteById(contactId);
    }
}