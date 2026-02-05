package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.requests.RentRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ISaveRentUseCase {
    void saveRent(RentRequest rentRequest, MultipartFile contract);
}