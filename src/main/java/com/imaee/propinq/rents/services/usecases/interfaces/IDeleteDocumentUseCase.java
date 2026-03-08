package com.imaee.propinq.rents.services.usecases.interfaces;

import java.util.UUID;

public interface IDeleteDocumentUseCase {
    void deleteDocument(UUID documentId);
}
