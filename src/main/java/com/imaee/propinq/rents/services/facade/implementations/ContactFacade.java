package com.imaee.propinq.rents.services.facade.implementations;

import com.imaee.propinq.rents.data.models.Contact;
import com.imaee.propinq.rents.services.facade.IContactFacade;
import com.imaee.propinq.users.services.usecases.interfaces.IGetLoggedUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.imaee.propinq.rents.Constants.WRONG_CONTACT_SUBJECT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class ContactFacade implements IContactFacade {

    private final IGetLoggedUserUseCase getLoggedUserUseCase;

    @Override
    public void throwExceptionIfContactSubjectIsNotLoggedUser(Contact contact) {
        final var user = getLoggedUserUseCase.getLoggedUser();
        if (!user.getUserId().equals(contact.getProperty().getUser().getUserId()))
            throw new ResponseStatusException(BAD_REQUEST, WRONG_CONTACT_SUBJECT);
    }
}