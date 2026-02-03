package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.notifications.services.interfaces.INotificationService;
import com.imaee.propinq.rents.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.rents.data.models.Contact;
import com.imaee.propinq.rents.data.repositories.IContactRepository;
import com.imaee.propinq.rents.services.facade.IContactFacade;
import com.imaee.propinq.rents.services.usecases.interfaces.IAnswerContactUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.IFindContactByIdUseCase;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.usecases.interfaces.IGetLoggedUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.imaee.propinq.notifications.Constants.ACCEPT_CONTACT_NOTIFICATION_TITLE;
import static com.imaee.propinq.notifications.Constants.REJECT_CONTACT_NOTIFICATION_TITLE;
import static com.imaee.propinq.notifications.data.enums.NotificationType.CONTACT_ACCEPTED;
import static com.imaee.propinq.notifications.data.enums.NotificationType.CONTACT_REJECTED;
import static com.imaee.propinq.notifications.mappers.NotificationMapper.buildNotification;
import static com.imaee.propinq.rents.data.enums.ContactState.getContactState;
import static java.time.LocalDateTime.now;

@Component
@AllArgsConstructor
public class AnswerContactUseCase implements IAnswerContactUseCase {

    private final IFindContactByIdUseCase findContactByIdUseCase;
    private final IContactFacade contactFacade;
    private final IGetLoggedUserUseCase getLoggedUserUseCase;
    private final IContactRepository contactRepository;
    private final INotificationService notificationService;

    @Override
    public void answerContactRequest(UUID contactId, AnswerContactRequest answerContactRequest) {
        final var contact = findContactByIdUseCase.findContactById(contactId);
        contactFacade.throwExceptionIfContactSubjectIsNotLoggedUser(contact);
        answerContactRequest(contact, answerContactRequest);
        notifyContactIssuer(contact, getLoggedUserUseCase.getLoggedUser());
    }

    private void answerContactRequest(Contact contact, AnswerContactRequest answerContactRequest) {
        contact.setContactAnswer(answerContactRequest.answer());
        contact.setState(getContactState(answerContactRequest.newState()));
        contact.setAnswerDate(now());
        contactRepository.save(contact);
    }

    private void notifyContactIssuer(Contact contact, User subject) {
        final var contactUrl = "/contact-details/" + contact.getContactId();
        notificationService.saveNotification(
                buildNotification(
                        subject,
                        contact.getIssuer(),
                        contact,
                        contact.isAccepted() ? CONTACT_ACCEPTED : CONTACT_REJECTED,
                        contact.isAccepted() ? ACCEPT_CONTACT_NOTIFICATION_TITLE : REJECT_CONTACT_NOTIFICATION_TITLE,
                        contact.getContactAnswer(),
                        contactUrl
                )
        );
    }
}