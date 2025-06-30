package com.imaee.propinq.neighborhood.services.implementations;

import com.imaee.propinq.neighborhood.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhood.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhood.data.models.Locality;
import com.imaee.propinq.neighborhood.data.models.Neighborhood;
import com.imaee.propinq.neighborhood.data.repositories.ILocalityRepository;
import com.imaee.propinq.neighborhood.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhood.mappers.NeighborhoodMapper;
import com.imaee.propinq.neighborhood.services.interfaces.INeighborhoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NeighborhoodService implements INeighborhoodService {
    private INeighborhoodRepository neighborhoodRepository;

    private ILocalityRepository localityRepository;

    @Override
    public ResponseEntity<Void> createNeighborhood(NeighborhoodRequest neighborhoodRequest) {
        final Locality neighborhoodRequestLocality = getNeighborhoodLocality(neighborhoodRequest);
        throwExceptionIfNeighborhoodExistsByNameAndLocality(neighborhoodRequest.name(), neighborhoodRequestLocality);

        final var neighborhood = NeighborhoodMapper.toNeighborhood(
                neighborhoodRequest.name(),
                neighborhoodRequestLocality
        );

       neighborhoodRepository.save(neighborhood);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<NeighborhoodResponse>> getNeighborhoods() {
        return ResponseEntity.ok(
                neighborhoodRepository
                        .findAll()
                        .stream()
                        .map(NeighborhoodMapper::toNeighborhoodResponse)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<NeighborhoodResponse> getNeighborhood(UUID id) {
        return ResponseEntity.ok(NeighborhoodMapper.toNeighborhoodResponse(findByIdOrThrowException(id)));
    }

    @Override
    public  ResponseEntity<Void> updateNeighborhood(UUID id, NeighborhoodRequest neighborhoodRequest) {
        Neighborhood neighborhood = findByIdOrThrowException(id);

        final Locality neighborhoodRequestLocality = getNeighborhoodLocality(neighborhoodRequest);
        throwExceptionIfNeighborhoodExistsByNameAndLocality(neighborhoodRequest.name(), neighborhoodRequestLocality);

        neighborhood.setName(neighborhoodRequest.name());
        neighborhood.setLocality(neighborhoodRequestLocality);

        neighborhoodRepository.save(neighborhood);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteNeighborhood(UUID id) {
        neighborhoodRepository.delete(findByIdOrThrowException(id));

        return ResponseEntity.ok().build();
    }

    private Locality getNeighborhoodLocality(NeighborhoodRequest neighborhoodRequest) {
        Optional<Locality> neighborhoodLocality = localityRepository.findById(neighborhoodRequest.localityId());
        if (neighborhoodLocality.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ILocalityRepository.MSG_NOT_EXISTS);
        }
        return neighborhoodLocality.get();
    }

    private void throwExceptionIfNeighborhoodExistsByNameAndLocality(String name, Locality locality) {
        if (neighborhoodRepository.existsByNameIgnoreCaseAndLocality(name, locality)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INeighborhoodRepository.MSG_ALREADY_EXISTS);
        }
    }

    private Neighborhood findByIdOrThrowException(UUID id) {
        Optional<Neighborhood> existingNeighborhood = neighborhoodRepository.findById(id);
        if (existingNeighborhood.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INeighborhoodRepository.MSG_NOT_EXISTS);
        }
        return existingNeighborhood.get();
    }
}
