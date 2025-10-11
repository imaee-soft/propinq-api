package com.imaee.propinq.localities.services.interfaces;

import com.imaee.propinq.localities.controllers.requests.LocalityRequest;
import com.imaee.propinq.localities.controllers.responses.LocalityResponse;


import java.util.List;
import java.util.UUID;

public interface ILocalityService {
    void createLocality(LocalityRequest LocalityRequest);

    List<LocalityResponse> getLocalities();

    LocalityResponse getLocality(UUID localityId);

    void updateLocality(LocalityRequest updatedLocalityRequest,UUID localityId);

    void deleteLocality(UUID localityId);

    void recoverLocality(UUID localityId);

    List<LocalityResponse> getLocalitiesByProvince(UUID provinceId);

}
