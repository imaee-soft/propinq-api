package com.imaee.propinq.neighborhood.services.implementations;

import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import com.imaee.propinq.neighborhood.data.models.Locality;
import com.imaee.propinq.neighborhood.data.repositories.ILocalityRepository;
import com.imaee.propinq.neighborhood.mappers.LocalityMapper;
import com.imaee.propinq.neighborhood.services.interfaces.ILocalityService;
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
public class LocalityService implements ILocalityService {

    @Autowired
    private ILocalityRepository repository;

    private final String MSG_ALREADY_EXISTS = "LOCALITY WITH THIS NAME ALREADY EXISTS";
    private final String MSG_NOT_EXISTS = "LOCALITY WITH THIS ID NOT EXISTS";

    @Override
    public ResponseEntity<Void> createLocality(LocalityRequest localityRequest) {

        Locality newLocality = LocalityMapper.toLocality(
                localityRequest.name()
        );

        Optional<Locality> existingLocality = repository.findByName(newLocality.getName());

        if (existingLocality.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_ALREADY_EXISTS);
        }

        repository.save(newLocality);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<LocalityResponse>> getLocalities() {
        return ResponseEntity.ok(
                repository
                        .findAll()
                        .stream()
                        .map(LocalityMapper::toLocalityResponse)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<LocalityResponse> getLocality(UUID id) {

        Optional<Locality> existingLocality = repository.findById(id);

        if (existingLocality.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_NOT_EXISTS);
        }

        return ResponseEntity.ok(LocalityMapper.toLocalityResponse(existingLocality.get()));
    }

}
