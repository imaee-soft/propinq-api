package com.imaee.propinq.contacts.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.contacts.controllers.requests.CancelContactRequest;
import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.contacts.data.repositories.IContactRepository;
import com.imaee.propinq.contacts.services.facade.IContactFacade;
import com.imaee.propinq.contacts.services.usecases.interfaces.ICancelContactUseCase;
import com.imaee.propinq.contacts.services.usecases.interfaces.IFindContactByIdUseCase;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.imaee.propinq.contacts.data.enums.ContactState.UNSETTLED;
import static com.imaee.propinq.notifications.Constants.CANCEL_CONTACT_NOTIFICATION_TITLE;
import static com.imaee.propinq.notifications.data.enums.NotificationType.CONTACT_CANCELLED;
import static com.imaee.propinq.notifications.mappers.NotificationMapper.buildNotification;
import static java.time.LocalDateTime.now;

@Component
@AllArgsConstructor
public class CancelContactUseCase implements ICancelContactUseCase {

    private final IFindContactByIdUseCase findContactByIdUseCase;
    private final IContactFacade contactFacade;
    private final IContactRepository contactRepository;
    private final IAuthenticatedUserService authenticatedUserService;
    private final INotificationService notificationService;

    @Override
    public void cancelContact(UUID contactId, CancelContactRequest cancelContactRequest) {
        final var contact = findContactByIdUseCase.findContactById(contactId);
        final var user = authenticatedUserService.getLoggedUserOrThrowException();
        contactFacade.throwExceptionIfContactSubjectIsNotLoggedUser(contact, user);
        cancelContact(contact, cancelContactRequest);
        notifyContactIssuer(contact, user);
    }

    private void cancelContact(Contact contact, CancelContactRequest cancelContactRequest) {
        contact.setCancellationReason(cancelContactRequest.answer());
        contact.setCancellationDate(now());
        contact.setState(UNSETTLED);
        contactRepository.save(contact);
    }

    private void notifyContactIssuer(Contact contact, User subject) {
        final var contactUrl = "/contact-details/" + contact.getContactId();
        notificationService.saveNotification(
                buildNotification(
                        subject,
                        contact.getIssuer(),
                        contact,
                        CONTACT_CANCELLED,
                        CANCEL_CONTACT_NOTIFICATION_TITLE,
                        contact.getCancellationReason(),
                        contactUrl
                )
        );
    }
}
