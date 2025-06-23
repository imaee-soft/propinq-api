package com.imaee.propinq.neighborhood.services.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ILocalityService {
    ResponseEntity<Void> createLocality(LocalityRequest newLocalityRequest);

    ResponseEntity<List<LocalityResponse>> getLocalities();

    ResponseEntity<LocalityResponse> getLocality(UUID id);

    ResponseEntity<Void> updateLocality(UUID id, LocalityRequest updatedLocalityRequest);

    ResponseEntity<Void> deleteLocality(UUID id);
}
