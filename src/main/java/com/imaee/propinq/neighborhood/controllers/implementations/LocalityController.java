package com.imaee.propinq.neighborhood.controllers.implementations;

import com.imaee.propinq.neighborhood.controllers.interfaces.ILocalityController;
import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import com.imaee.propinq.neighborhood.services.interfaces.ILocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalityController implements ILocalityController {

    @Autowired
    private ILocalityService service;

    @Override
    public LocalityResponse create(LocalityRequest localityRequest) {
        return service.create(localityRequest);
    }

}
