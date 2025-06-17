package com.imaee.propinq.neighborhood.services.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;

public interface ILocalityService {

    LocalityResponse create(LocalityRequest localityRequest);

}
