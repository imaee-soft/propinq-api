package com.imaee.propinq.neighborhoods.services.usecases.interfaces;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;

public interface ICreateNeighborhoodUseCase {
    void createNeighborhood(NeighborhoodRequest neighborhoodRequest);
    Locality getNeighborhoodLocality(NeighborhoodRequest neighborhoodRequest);
    void throwExceptionIfNeighborhoodExistsByNameAndLocality(String name, Locality locality);
}
