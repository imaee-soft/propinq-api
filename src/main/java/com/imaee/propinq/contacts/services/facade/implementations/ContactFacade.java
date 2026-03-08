package com.imaee.propinq.contacts.services.facade.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.contacts.services.facade.IContactFacade;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.imaee.propinq.contacts.Constants.WRONG_CONTACT_SUBJECT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class ContactFacade implements IContactFacade {

    private final IAuthenticatedUserService authenticatedUserService;

    @Override
    public void throwExceptionIfContactSubjectIsNotLoggedUser(Contact contact) {
        final var user = authenticatedUserService.getLoggedUserOrThrowException();
        throwExceptionIfContactSubjectIsNotLoggedUser(contact, user);
    }

    @Override
    public void throwExceptionIfContactSubjectIsNotLoggedUser(Contact contact, User user) {
        if (!user.getUserId().equals(contact.getProperty().getUser().getUserId()))
            throw new ResponseStatusException(BAD_REQUEST, WRONG_CONTACT_SUBJECT);
    }
}