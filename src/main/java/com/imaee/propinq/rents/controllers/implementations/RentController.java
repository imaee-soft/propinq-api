package com.imaee.propinq.rents.controllers.implementations;

import com.imaee.propinq.rents.controllers.interfaces.IRentController;
import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import com.imaee.propinq.rents.services.interfaces.IRentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class RentController implements IRentController {

    private final IRentService rentService;

    @Override
    public void saveRent(RentRequest rentRequest, MultipartFile contract) {
        rentService.saveRent(rentRequest, contract);
    }

    @Override
    public Page<SimpleRent> getOwnerRents(Integer pageNumber, Integer pageSize) {
        return rentService.getOwnerRents(pageNumber, pageSize);
    }
}