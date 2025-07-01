package com.imaee.propinq.neighborhood.services.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhood.controllers.responses.NeighborhoodResponse;

import java.util.List;
import java.util.UUID;

public interface INeighborhoodService {
    void createNeighborhood(NeighborhoodRequest newNeighborhoodRequest);

    List<NeighborhoodResponse> getNeighborhoods();

    NeighborhoodResponse getNeighborhood(UUID id);

    void updateNeighborhood(UUID id, NeighborhoodRequest updatedNeighborhoodRequest);

    void deleteNeighborhood(UUID id);
}
