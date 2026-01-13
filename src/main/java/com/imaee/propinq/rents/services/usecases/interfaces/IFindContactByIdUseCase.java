package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.data.models.Contact;

import java.util.UUID;

public interface IFindContactByIdUseCase {
    Contact findContactById(UUID contactId);
}