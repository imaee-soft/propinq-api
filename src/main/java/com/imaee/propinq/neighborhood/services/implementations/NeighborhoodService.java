package com.imaee.propinq.neighborhood.services.implementations;

import com.imaee.propinq.neighborhood.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhood.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhood.data.models.Locality;
import com.imaee.propinq.neighborhood.data.models.Neighborhood;
import com.imaee.propinq.neighborhood.data.repositories.ILocalityRepository;
import com.imaee.propinq.neighborhood.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhood.mappers.NeighborhoodMapper;
import com.imaee.propinq.neighborhood.services.interfaces.INeighborhoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NeighborhoodService implements INeighborhoodService {
    @Autowired
    private INeighborhoodRepository neighborhoodRepository;

    @Autowired
    private ILocalityRepository localityRepository;

    @Override
    public ResponseEntity<Void> createNeighborhood(NeighborhoodRequest neighborhoodRequest) {
        Optional<Locality> neighborhoodRequestLocality = localityRepository.findById(neighborhoodRequest.localityId());
        if (neighborhoodRequestLocality.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ILocalityRepository.MSG_NOT_EXISTS);
        }

        Optional<Neighborhood> existingNeighborhood = neighborhoodRepository.findByNameIgnoreCaseAndLocality(
                neighborhoodRequest.name(),
                neighborhoodRequestLocality.get()
        );
        if (existingNeighborhood.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INeighborhoodRepository.MSG_ALREADY_EXISTS);
        }

        Neighborhood neighborhood = NeighborhoodMapper.toNeighborhood(
                neighborhoodRequest.name(),
                neighborhoodRequestLocality.get()
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
        Optional<Neighborhood> existingNeighborhood = neighborhoodRepository.findById(id);
        if (existingNeighborhood.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INeighborhoodRepository.MSG_NOT_EXISTS);
        }

        return ResponseEntity.ok(NeighborhoodMapper.toNeighborhoodResponse(existingNeighborhood.get()));
    }

    @Override
    public  ResponseEntity<Void> updateNeighborhood(UUID id, NeighborhoodRequest neighborhoodRequest) {
        Optional<Locality> neighborhoodRequestLocality = localityRepository.findById(neighborhoodRequest.localityId());
        if (neighborhoodRequestLocality.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ILocalityRepository.MSG_NOT_EXISTS);
        }

        Optional<Neighborhood> existingNeighborhood = neighborhoodRepository.findById(id);
        if (existingNeighborhood.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INeighborhoodRepository.MSG_NOT_EXISTS);
        }

        Neighborhood neighborhood = existingNeighborhood.get();

        neighborhood.setName(neighborhoodRequest.name());
        neighborhood.setLocality(neighborhoodRequestLocality.get());

        neighborhoodRepository.save(neighborhood);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteNeighborhood(UUID id) {
        Optional<Neighborhood> existingNeighborhood = neighborhoodRepository.findById(id);
        if (existingNeighborhood.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INeighborhoodRepository.MSG_NOT_EXISTS);
        }

        neighborhoodRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
