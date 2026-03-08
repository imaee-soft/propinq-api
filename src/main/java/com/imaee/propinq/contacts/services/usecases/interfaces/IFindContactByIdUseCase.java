package com.imaee.propinq.contacts.services.usecases.interfaces;

import com.imaee.propinq.contacts.data.models.Contact;

import java.util.UUID;

public interface IFindContactByIdUseCase {
    Contact findContactById(UUID contactId);
}