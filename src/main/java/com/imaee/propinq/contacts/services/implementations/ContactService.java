package com.imaee.propinq.contacts.services.implementations;

import com.imaee.propinq.contacts.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.contacts.controllers.requests.CancelContactRequest;
import com.imaee.propinq.contacts.controllers.requests.ContactRequest;
import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.contacts.controllers.responses.ContactResponse;
import com.imaee.propinq.contacts.services.interfaces.IContactService;
import com.imaee.propinq.contacts.services.usecases.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ContactService implements IContactService {

    private final IGetTenantContactsUseCase getTenantContactsUseCase;
    private final IGetOwnerContactsUseCase getOwnerContactsUseCase;
    private final ISaveContactUseCase saveContactUseCase;
    private final IGetContactUseCase getContactUseCase;
    private final IGetContactDetailsUseCase getContactDetailsUseCase;
    private final IAnswerContactUseCase answerContactUseCase;
    private final ICancelContactUseCase cancelContactUseCase;
    private final IDeleteContactUseCase deleteContactUseCase;

    @Override
    public Page<ContactDetailResponse> getTenantContacts(Integer pageNumber, Integer pageSize) {
        return getTenantContactsUseCase.getTenantContacts(pageNumber, pageSize);
    }

    @Override
    public Page<ContactDetailResponse> getOwnerContacts(Integer pageNumber, Integer pageSize, String surname, String status) {
        return getOwnerContactsUseCase.getOwnerContacts(pageNumber, pageSize, surname, status);
    }

    @Override
    public void saveContactRequest(ContactRequest contactRequest) {
        saveContactUseCase.saveContactRequest(contactRequest);
    }

    @Override
    public ContactResponse getContactRequest(UUID contactId) {
        return getContactUseCase.getContactRequest(contactId);
    }

    @Override
    public ContactDetailResponse getContactDetails(UUID contactId) {
        return getContactDetailsUseCase.getContactDetails(contactId);
    }

    @Override
    public void answerContactRequest(UUID contactId, AnswerContactRequest answerContactRequest) {
        answerContactUseCase.answerContactRequest(contactId, answerContactRequest);
    }

    @Override
    public void cancelContact(UUID contactId, CancelContactRequest cancelContactRequest) {
        cancelContactUseCase.cancelContact(contactId, cancelContactRequest);
    }

    @Override
    public void deleteContactRequest(UUID contactId) {
        deleteContactUseCase.deleteContactRequest(contactId);
    }
}