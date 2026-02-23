package com.imaee.propinq.contacts.controllers.implementations;

import com.imaee.propinq.contacts.controllers.interfaces.IContactController;
import com.imaee.propinq.contacts.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.contacts.controllers.requests.CancelContactRequest;
import com.imaee.propinq.contacts.controllers.requests.ContactRequest;
import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.contacts.controllers.responses.ContactResponse;
import com.imaee.propinq.contacts.services.interfaces.IContactService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class ContactController implements IContactController {

    private final IContactService contactService;

    @Override
    public Page<ContactDetailResponse> getTenantContacts(Integer pageNumber, Integer pageSize) {
        return contactService.getTenantContacts(pageNumber, pageSize);
    }

    @Override
    public Page<ContactDetailResponse> getOwnerContacts(Integer pageNumber, Integer pageSize, String surname, String status) {
        return contactService.getOwnerContacts(pageNumber, pageSize, surname, status);
    }

    @Override
    public void saveContactRequest(ContactRequest contactRequest) {
        contactService.saveContactRequest(contactRequest);
    }

    @Override
    public ContactResponse getContactRequest(UUID contactId) {
        return contactService.getContactRequest(contactId);
    }

    @Override
    public ContactDetailResponse getContactDetails(UUID contactId) {
        return contactService.getContactDetails(contactId);
    }

    @Override
    public void answerContactRequest(UUID contactId, AnswerContactRequest answerContactRequest) {
        contactService.answerContactRequest(contactId, answerContactRequest);
    }

    @Override
    public void cancelContact(UUID contactId, CancelContactRequest cancelContactRequest) {
        contactService.cancelContact(contactId, cancelContactRequest);
    }

    @Override
    public void deleteContactRequest(UUID contactId) {
        contactService.deleteContactRequest(contactId);
    }
}