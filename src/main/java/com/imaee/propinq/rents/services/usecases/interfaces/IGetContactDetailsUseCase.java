package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.responses.ContactDetailResponse;

import java.util.UUID;

public interface IGetContactDetailsUseCase {
    ContactDetailResponse getContactDetails(UUID contactId);
}