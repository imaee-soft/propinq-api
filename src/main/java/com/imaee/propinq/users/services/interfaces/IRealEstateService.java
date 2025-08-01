package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.controllers.requests.RealEstateRequest;
import com.imaee.propinq.users.controllers.responses.RealEstateResponse;

public interface IRealEstateService {
    RealEstateResponse registerRealEstate(RealEstateRequest realEstateRequest);

}
