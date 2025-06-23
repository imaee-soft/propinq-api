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
    private ILocalityService localityService;

    @Override
    public ResponseEntity<Void> createLocality(LocalityRequest newLocalityRequest) {
        return localityService.createLocality(newLocalityRequest);
    }

    @Override
    public ResponseEntity<List<LocalityResponse>> getLocalities() {
        return localityService.getLocalities();
    }

    @Override
    public ResponseEntity<LocalityResponse> getLocality(UUID id) {
        return localityService.getLocality(id);
    }

    @Override
    public ResponseEntity<Void> updateLocality(UUID id, LocalityRequest updatedLocalityRequest) {
        return localityService.updateLocality(id, updatedLocalityRequest);
    }

    @Override
    public ResponseEntity<Void> deleteLocality(UUID id) {
        return localityService.deleteLocality(id);
    }
}
