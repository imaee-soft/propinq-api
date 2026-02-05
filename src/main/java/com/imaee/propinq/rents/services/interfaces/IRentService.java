package com.imaee.propinq.rents.services.interfaces;

import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IRentService {
    void saveRent(RentRequest rentRequest, MultipartFile contract);
    Page<SimpleRent> getOwnerRents(Integer pageNumber, Integer pageSize);
}