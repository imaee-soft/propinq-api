package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.SaveRentResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ISaveRentUseCase {
    SaveRentResponse saveRent(RentRequest rentRequest, MultipartFile contract);
}