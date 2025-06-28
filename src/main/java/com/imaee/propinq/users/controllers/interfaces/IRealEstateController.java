package com.imaee.propinq.users.controllers.interfaces;

import com.imaee.propinq.users.controllers.requests.RealEstateRequest;
import com.imaee.propinq.users.controllers.responses.RealEstateResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/real-estates")
public interface IRealEstateController {

    @PostMapping("/registerRealEstate")
    RealEstateResponse registerRealEstate(@RequestBody @Valid RealEstateRequest realEstateRequest);
}
