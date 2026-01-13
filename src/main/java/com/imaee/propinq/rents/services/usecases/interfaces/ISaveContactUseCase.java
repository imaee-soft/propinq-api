package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.requests.ContactRequest;

public interface ISaveContactUseCase {
    void saveContactRequest(ContactRequest contactRequest);
}