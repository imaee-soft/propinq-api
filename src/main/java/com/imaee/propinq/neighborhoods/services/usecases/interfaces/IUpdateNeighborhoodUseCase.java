package com.imaee.propinq.neighborhoods.services.usecases.interfaces;

import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhoods.data.models.Neighborhood;

import java.util.UUID;

public interface IUpdateNeighborhoodUseCase {
    void updateNeighborhood(NeighborhoodRequest neighborhoodRequest, UUID neighborhoodId);
    void throwExceptionIfNeighborhoodIsDeleted(Neighborhood neighborhood);
}
