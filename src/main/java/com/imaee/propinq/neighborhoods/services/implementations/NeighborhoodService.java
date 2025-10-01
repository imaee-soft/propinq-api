package com.imaee.propinq.neighborhoods.services.implementations;

import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.services.interfaces.INeighborhoodService;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NeighborhoodService implements INeighborhoodService {

    private final ICreateNeighborhoodUseCase createNeighborhoodUseCase;
    private final IGetNeighborhoodsUseCase getNeighborhoodsUseCase;
    private final IGetNeighborhoodUseCase getNeighborhoodUseCase;
    private final IUpdateNeighborhoodUseCase updateNeighborhoodUseCase;
    private final IDeleteNeighborhoodUseCase deleteNeighborhoodUseCase;
    private final IRecoverNeighborhoodUseCase recoverNeighborhoodUseCase;

    @Override
    public void createNeighborhood(NeighborhoodRequest neighborhoodRequest) {
        createNeighborhoodUseCase.createNeighborhood(neighborhoodRequest);
    }

    @Override
    public List<NeighborhoodResponse> getNeighborhoods() {
        return getNeighborhoodsUseCase.getNeighborhoods();
    }

    @Override
    public NeighborhoodResponse getNeighborhood(UUID neighborhoodId) {
        return getNeighborhoodUseCase.getNeighborhood(neighborhoodId);
    }

    @Override
    public void updateNeighborhood(NeighborhoodRequest neighborhoodRequest,UUID neighborhoodId) {
        updateNeighborhoodUseCase.updateNeighborhood(neighborhoodRequest, neighborhoodId);
    }

    @Override
    public void deleteNeighborhood(UUID neighborhoodId) {
        deleteNeighborhoodUseCase.deleteNeighborhood(neighborhoodId);
    }

    @Override
    public void recoverNeighborhood(UUID neighborhoodId) {
        recoverNeighborhoodUseCase.recoverNeighborhood(neighborhoodId);
    }


}
