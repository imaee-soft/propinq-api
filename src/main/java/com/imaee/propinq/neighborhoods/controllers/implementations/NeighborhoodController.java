package com.imaee.propinq.neighborhoods.controllers.implementations;

import com.imaee.propinq.neighborhoods.controllers.interfaces.INeighborhoodController;
import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.services.interfaces.INeighborhoodService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class NeighborhoodController implements INeighborhoodController {

    private INeighborhoodService neighborhoodService;

    @Override
    public void createNeighborhood(NeighborhoodRequest neighborhoodRequest) {
        neighborhoodService.createNeighborhood(neighborhoodRequest);
    }

    @Override
    public List<NeighborhoodResponse> getNeighborhoods() {
        return neighborhoodService.getNeighborhoods();
    }

    @Override
    public NeighborhoodResponse getNeighborhood(UUID neighborhoodId) {
        return neighborhoodService.getNeighborhood(neighborhoodId);
    }

    @Override
    public void updateNeighborhood(NeighborhoodRequest neighborhoodRequest,UUID neighborhoodId) {
        neighborhoodService.updateNeighborhood(neighborhoodRequest, neighborhoodId);
    }

    @Override
    public void deleteNeighborhood(UUID neighborhoodId) {
        neighborhoodService.deleteNeighborhood(neighborhoodId);
    }

    @Override
    public void recoverNeighborhood(UUID neighborhoodId) {
        neighborhoodService.recoverNeighborhood(neighborhoodId);
    }

}
