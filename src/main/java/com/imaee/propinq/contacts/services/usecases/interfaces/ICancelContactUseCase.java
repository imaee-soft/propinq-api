package com.imaee.propinq.contacts.services.usecases.interfaces;

import com.imaee.propinq.contacts.controllers.requests.CancelContactRequest;

import java.util.UUID;

public interface ICancelContactUseCase {
    void cancelContact(UUID contactId, CancelContactRequest cancelContactRequest);
}