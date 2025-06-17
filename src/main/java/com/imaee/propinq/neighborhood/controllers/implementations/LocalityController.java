package com.imaee.propinq.neighborhood.controllers.implementations;

import com.imaee.propinq.neighborhood.controllers.interfaces.ILocalityController;
import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import com.imaee.propinq.neighborhood.services.interfaces.ILocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class LocalityController implements ILocalityController {

    @Autowired
    private ILocalityService service;

    @Override
    public ResponseEntity<Void> createLocality(LocalityRequest localityRequest) {
        return service.createLocality(localityRequest);
    }

    @Override
    public ResponseEntity<List<LocalityResponse>> getLocalities() {
        return service.getLocalities();
    }

    @Override
    public ResponseEntity<LocalityResponse> getLocality(UUID id) {
        return service.getLocality(id);
    }

}
