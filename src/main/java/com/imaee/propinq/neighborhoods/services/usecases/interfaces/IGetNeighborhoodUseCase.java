package com.imaee.propinq.neighborhoods.services.usecases.interfaces;

import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.data.models.Neighborhood;

import java.util.UUID;

public interface IGetNeighborhoodUseCase {
    NeighborhoodResponse getNeighborhood(UUID neighborhoodId);
    Neighborhood findNeighborhoodByIdOrThrowException(UUID id);
}
