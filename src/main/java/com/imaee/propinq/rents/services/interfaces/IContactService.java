package com.imaee.propinq.rents.services.interfaces;

import com.imaee.propinq.rents.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.rents.controllers.requests.ContactRequest;
import com.imaee.propinq.rents.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.rents.controllers.responses.ContactResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IContactService {
    Page<ContactDetailResponse> getTenantContacts(Integer pageNumber, Integer pageSize);
    Page<ContactDetailResponse> getOwnerContacts(Integer pageNumber, Integer pageSize);
    void saveContactRequest(ContactRequest contactRequest);
    ContactResponse getContactRequest(UUID contactId);
    ContactDetailResponse getContactDetails(UUID contactId);
    void answerContactRequest(UUID contactId, AnswerContactRequest answerContactRequest);
    void deleteContactRequest(UUID contactId);
}