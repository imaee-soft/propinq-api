package com.imaee.propinq.contacts.services.usecases.interfaces;

import java.util.UUID;

public interface IDeleteContactUseCase {
    void deleteContactRequest(UUID contactId);
}