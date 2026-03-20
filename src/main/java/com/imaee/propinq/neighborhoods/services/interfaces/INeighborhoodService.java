package com.imaee.propinq.neighborhoods.services.interfaces;

import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;

import java.util.List;
import java.util.UUID;

public interface INeighborhoodService {

    void createNeighborhood(NeighborhoodRequest neighborhoodRequest);
    List<NeighborhoodResponse> getNeighborhoods();
    NeighborhoodResponse getNeighborhood(UUID neighborhoodId);
    void updateNeighborhood(NeighborhoodRequest neighborhoodRequest,UUID neighborhoodId);
    void deleteNeighborhood(UUID neighborhoodId);
    void recoverNeighborhood(UUID neighborhoodId);
}
