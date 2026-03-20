package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.requests.RentDocumentRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ISaveDocumentUseCase {
    void saveDocument(RentDocumentRequest rentDocumentRequest, MultipartFile document);
}