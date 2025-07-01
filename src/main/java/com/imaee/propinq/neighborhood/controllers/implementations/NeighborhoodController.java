package com.imaee.propinq.neighborhood.controllers.implementations;

import com.imaee.propinq.neighborhood.controllers.interfaces.INeighborhoodController;
import com.imaee.propinq.neighborhood.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhood.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhood.services.interfaces.INeighborhoodService;
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
    public NeighborhoodResponse getNeighborhood(UUID id) {
        return neighborhoodService.getNeighborhood(id);
    }

    @Override
    public void updateNeighborhood(UUID id, NeighborhoodRequest neighborhoodRequest) {
        neighborhoodService.updateNeighborhood(id, neighborhoodRequest);
    }

    @Override
    public void deleteNeighborhood(UUID id) {
        neighborhoodService.deleteNeighborhood(id);
    }
}
