package com.imaee.propinq.contacts.services.usecases.implementations;

import com.imaee.propinq.contacts.controllers.responses.ContactResponse;
import com.imaee.propinq.contacts.services.facade.IContactFacade;
import com.imaee.propinq.contacts.services.usecases.interfaces.IFindContactByIdUseCase;
import com.imaee.propinq.contacts.services.usecases.interfaces.IGetContactUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.imaee.propinq.contacts.mappers.ContactMapper.buildContactResponse;

@Component
@AllArgsConstructor
public class GetContactUseCase implements IGetContactUseCase {

    private final IFindContactByIdUseCase findContactByIdUseCase;
    private final IContactFacade contactFacade;

    @Override
    public ContactResponse getContactRequest(UUID contactId) {
        final var contact = findContactByIdUseCase.findContactById(contactId);
        contactFacade.throwExceptionIfContactSubjectIsNotLoggedUser(contact);
        return buildContactResponse(contact);
    }
}