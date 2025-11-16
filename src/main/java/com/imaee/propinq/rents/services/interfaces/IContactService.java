package com.imaee.propinq.rents.services.interfaces;

import com.imaee.propinq.rents.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.rents.controllers.requests.ContactRequest;
import com.imaee.propinq.rents.controllers.responses.ContactResponse;

import java.util.UUID;

public interface IContactService {
    void saveContactRequest(ContactRequest contactRequest);
    ContactResponse getContactRequest(UUID contactId);
    void answerContactRequest(UUID contactId, AnswerContactRequest answerContactRequest);
}