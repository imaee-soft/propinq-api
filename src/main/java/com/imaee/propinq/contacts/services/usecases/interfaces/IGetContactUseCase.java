package com.imaee.propinq.contacts.services.usecases.interfaces;

import com.imaee.propinq.contacts.controllers.responses.ContactResponse;

import java.util.UUID;

public interface IGetContactUseCase {
    ContactResponse getContactRequest(UUID contactId);
}