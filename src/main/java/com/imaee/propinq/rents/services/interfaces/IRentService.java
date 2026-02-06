package com.imaee.propinq.rents.services.interfaces;

import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.RentDetail;
import com.imaee.propinq.rents.controllers.responses.SaveRentResponse;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IRentService {
    SaveRentResponse saveRent(RentRequest rentRequest, MultipartFile contract);
    Page<SimpleRent> getOwnerRents(Integer pageNumber, Integer pageSize);
    RentDetail getRent(UUID rentId);
}