package com.imaee.propinq.neighborhood.services.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhood.controllers.responses.NeighborhoodResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface INeighborhoodService {
    ResponseEntity<Void> createNeighborhood(NeighborhoodRequest newNeighborhoodRequest);

    ResponseEntity<List<NeighborhoodResponse>> getNeighborhoods();

    ResponseEntity<NeighborhoodResponse> getNeighborhood(UUID id);

    ResponseEntity<Void> updateNeighborhood(UUID id, NeighborhoodRequest updatedNeighborhoodRequest);

    ResponseEntity<Void> deleteNeighborhood(UUID id);
}
