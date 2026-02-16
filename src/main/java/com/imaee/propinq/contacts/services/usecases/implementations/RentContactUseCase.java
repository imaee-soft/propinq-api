package com.imaee.propinq.contacts.services.usecases.implementations;

import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.contacts.data.repositories.IContactRepository;
import com.imaee.propinq.contacts.services.usecases.interfaces.IRentContactUseCase;
import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.imaee.propinq.contacts.data.enums.ContactState.RENTED;
import static com.imaee.propinq.notifications.Constants.RENT_NOTIFICATION_TITLE;
import static com.imaee.propinq.notifications.data.enums.NotificationType.CONTACT_RENTED;
import static com.imaee.propinq.notifications.mappers.NotificationMapper.buildNotification;

@Component
@AllArgsConstructor
public class RentContactUseCase implements IRentContactUseCase {

    private final IContactRepository contactRepository;
    private final INotificationService notificationService;

    @Override
    public void markAsRented(Contact contact) {
        contact.setState(RENTED);
        contactRepository.save(contact);
        notifyContactIssuer(contact, contact.getProperty().getUser());
    }

    private void notifyContactIssuer(Contact contact, User subject) {
        final var contactUrl = "/contact-details/" + contact.getContactId();
        notificationService.saveNotification(
                buildNotification(
                        subject,
                        contact.getIssuer(),
                        CONTACT_RENTED,
                        RENT_NOTIFICATION_TITLE,
                        contact.getProperty().getTitle(),
                        contactUrl
                )
        );
    }
}