package com.imaee.propinq.rents.services.usecases.interfaces;

import java.util.UUID;

public interface IDeleteContactUseCase {
    void deleteContactRequest(UUID contactId);
}