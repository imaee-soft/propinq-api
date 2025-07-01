package com.imaee.propinq.neighborhood.services.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;

import java.util.List;
import java.util.UUID;

public interface ILocalityService {
    void createLocality(LocalityRequest newLocalityRequest);

    List<LocalityResponse> getLocalities();

    LocalityResponse getLocality(UUID id);

    void updateLocality(UUID id, LocalityRequest updatedLocalityRequest);

    void deleteLocality(UUID id);
}
