package com.imaee.propinq.localities.controllers.implementations;

import com.imaee.propinq.localities.controllers.interfaces.ILocalityController;
import com.imaee.propinq.localities.controllers.requests.LocalityRequest;
import com.imaee.propinq.localities.controllers.responses.LocalityResponse;
import com.imaee.propinq.localities.services.interfaces.ILocalityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
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
    public LocalityResponse getLocality(UUID localityId) {
        return localityService.getLocality(localityId);
    }

    @Override
    public void updateLocality(LocalityRequest localityRequest, UUID localityId) {
        localityService.updateLocality(localityRequest, localityId);
    }

    @Override
    public void deleteLocality(UUID localityId) {
        localityService.deleteLocality(localityId);
    }

    @Override
    public void recoverLocality(UUID localityId) {
        localityService.recoverLocality(localityId);
    }

    @Override
    public List<LocalityResponse> getLocalitiesByProvince(@PathVariable UUID provinceId) {
        return localityService.getLocalitiesByProvince(provinceId);
    }
}
