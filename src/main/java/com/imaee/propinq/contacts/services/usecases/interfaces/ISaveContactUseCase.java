package com.imaee.propinq.contacts.services.usecases.interfaces;

import com.imaee.propinq.contacts.controllers.requests.ContactRequest;

public interface ISaveContactUseCase {
    void saveContactRequest(ContactRequest contactRequest);
}