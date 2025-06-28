package com.imaee.propinq.users.controllers.implementations;

import com.imaee.propinq.users.controllers.interfaces.IRealEstateController;
import com.imaee.propinq.users.controllers.requests.RealEstateRequest;
import com.imaee.propinq.users.controllers.responses.RealEstateResponse;
import com.imaee.propinq.users.services.interfaces.IRealEstateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class RealEstateController implements IRealEstateController {

    private IRealEstateService realEstateService;

    @Override
    public RealEstateResponse registerRealEstate(@RequestBody @Valid RealEstateRequest realEstateRequest) {
        return realEstateService.registerRealEstate(realEstateRequest);
    }

}
