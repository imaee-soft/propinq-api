package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.responses.ContactResponse;

import java.util.UUID;

public interface IGetContactUseCase {
    ContactResponse getContactRequest(UUID contactId);
}