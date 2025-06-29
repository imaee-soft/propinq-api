package com.imaee.propinq.neighborhood.controllers.implementations;

import com.imaee.propinq.neighborhood.controllers.interfaces.INeighborhoodController;
import com.imaee.propinq.neighborhood.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhood.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhood.services.interfaces.INeighborhoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class NeighborhoodController implements INeighborhoodController {
    private INeighborhoodService neighborhoodService;

    @Override
    public ResponseEntity<Void> createNeighborhood(NeighborhoodRequest neighborhoodRequest) {
        return neighborhoodService.createNeighborhood(neighborhoodRequest);
    }

    @Override
    public ResponseEntity<List<NeighborhoodResponse>> getNeighborhoods() {
        return neighborhoodService.getNeighborhoods();
    }

    @Override
    public ResponseEntity<NeighborhoodResponse> getNeighborhood(UUID id) {
        return neighborhoodService.getNeighborhood(id);
    }

    @Override
    public ResponseEntity<Void> updateNeighborhood(UUID id, NeighborhoodRequest neighborhoodRequest) {
        return neighborhoodService.updateNeighborhood(id, neighborhoodRequest);
    }

    @Override
    public ResponseEntity<Void> deleteNeighborhood(UUID id) {
        return neighborhoodService.deleteNeighborhood(id);
    }
}
