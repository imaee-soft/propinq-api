package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.services.usecases.interfaces.IFindPropertyByIdUseCase;
import com.imaee.propinq.rents.controllers.requests.ContactRequest;
import com.imaee.propinq.rents.data.models.Contact;
import com.imaee.propinq.rents.data.repositories.IContactRepository;
import com.imaee.propinq.rents.services.usecases.interfaces.ISaveContactUseCase;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.usecases.interfaces.IGetLoggedUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.imaee.propinq.notifications.Constants.CONTACT_ALREADY_EXISTS;
import static com.imaee.propinq.notifications.Constants.NEW_CONTACT_NOTIFICATION_TITLE;
import static com.imaee.propinq.notifications.data.enums.NotificationType.NEW_CONTACT_REQUEST;
import static com.imaee.propinq.notifications.mappers.NotificationMapper.buildNotification;
import static com.imaee.propinq.rents.mappers.ContactMapper.buildContact;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class SaveContactUseCase implements ISaveContactUseCase {

    private final IGetLoggedUserUseCase getLoggedUserUseCase;
    private final IFindPropertyByIdUseCase findPropertyByIdUseCase;
    private final IContactRepository contactRepository;
    private final INotificationService notificationService;

    @Override
    public void saveContactRequest(ContactRequest contactRequest) {
        final var issuer = getLoggedUserUseCase.getLoggedUser();
        final var property = findPropertyByIdUseCase.findProperty(contactRequest.propertyId());
        throwExceptionIfContactAlreadyExists(issuer, property);
        saveContactAndNotifyOwner(contactRequest, issuer, property);
    }

    private void throwExceptionIfContactAlreadyExists(User issuer, Property property) {
        if (contactRepository.existsByIssuerAndProperty(issuer, property))
            throw new ResponseStatusException(BAD_REQUEST, CONTACT_ALREADY_EXISTS);
    }

    private void saveContactAndNotifyOwner(ContactRequest contactRequest, User issuer, Property property) {
        final var contact = buildContact(contactRequest, issuer, property);
        contactRepository.save(contact);
        notifyOwner(issuer, property, contact);
    }

    private void notifyOwner(User user, Property property, Contact contact) {
        final var contactUrl = "/contact-details/" + contact.getContactId();
        notificationService.saveNotification(
                buildNotification(
                        user,
                        property.getUser(),
                        contact,
                        NEW_CONTACT_REQUEST,
                        NEW_CONTACT_NOTIFICATION_TITLE,
                        property.getAddress(),
                        contactUrl
                )
        );
    }
}