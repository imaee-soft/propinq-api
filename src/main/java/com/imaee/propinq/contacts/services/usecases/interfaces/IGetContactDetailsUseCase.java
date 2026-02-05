package com.imaee.propinq.contacts.services.usecases.interfaces;

import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;

import java.util.UUID;

public interface IGetContactDetailsUseCase {
    ContactDetailResponse getContactDetails(UUID contactId);
}