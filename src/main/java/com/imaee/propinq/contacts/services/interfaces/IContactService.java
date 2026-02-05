package com.imaee.propinq.contacts.services.interfaces;

import com.imaee.propinq.contacts.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.contacts.controllers.requests.CancelContactRequest;
import com.imaee.propinq.contacts.controllers.requests.ContactRequest;
import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.contacts.controllers.responses.ContactResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IContactService {
    Page<ContactDetailResponse> getTenantContacts(Integer pageNumber, Integer pageSize);
    Page<ContactDetailResponse> getOwnerContacts(Integer pageNumber, Integer pageSize, String status);
    void saveContactRequest(ContactRequest contactRequest);
    ContactResponse getContactRequest(UUID contactId);
    ContactDetailResponse getContactDetails(UUID contactId);
    void answerContactRequest(UUID contactId, AnswerContactRequest answerContactRequest);
    void cancelContact(UUID contactId, CancelContactRequest cancelContactRequest);
    void deleteContactRequest(UUID contactId);
}