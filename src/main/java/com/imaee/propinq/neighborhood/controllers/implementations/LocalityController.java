package com.imaee.propinq.neighborhood.controllers.implementations;

import com.imaee.propinq.neighborhood.controllers.interfaces.ILocalityController;
import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import com.imaee.propinq.neighborhood.services.interfaces.ILocalityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class LocalityController implements ILocalityController {
    private ILocalityService localityService;

    @Override
    public void createLocality(LocalityRequest localityRequest) {
        localityService.createLocality(localityRequest);
    }

    @Override
    public List<LocalityResponse> getLocalities() {
        return localityService.getLocalities();
    }

    @Override
    public LocalityResponse getLocality(UUID id) {
        return localityService.getLocality(id);
    }

    @Override
    public void updateLocality(UUID id, LocalityRequest localityRequest) {
        localityService.updateLocality(id, localityRequest);
    }

    @Override
    public void deleteLocality(UUID id) {
        localityService.deleteLocality(id);
    }
}
