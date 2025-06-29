package com.imaee.propinq.neighborhood.controllers.implementations;

import com.imaee.propinq.neighborhood.controllers.interfaces.ILocalityController;
import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import com.imaee.propinq.neighborhood.services.interfaces.ILocalityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class LocalityController implements ILocalityController {
    private ILocalityService localityService;

    @Override
    public ResponseEntity<Void> createLocality(LocalityRequest localityRequest) {
        return localityService.createLocality(localityRequest);
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
    public ResponseEntity<Void> updateLocality(UUID id, LocalityRequest localityRequest) {
        return localityService.updateLocality(id, localityRequest);
    }

    @Override
    public ResponseEntity<Void> deleteLocality(UUID id) {
        return localityService.deleteLocality(id);
    }
}
