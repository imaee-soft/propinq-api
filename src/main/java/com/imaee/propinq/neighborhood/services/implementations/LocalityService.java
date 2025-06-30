package com.imaee.propinq.neighborhood.services.implementations;

import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import com.imaee.propinq.neighborhood.data.models.Locality;
import com.imaee.propinq.neighborhood.data.models.Province;
import com.imaee.propinq.neighborhood.data.repositories.ILocalityRepository;
import com.imaee.propinq.neighborhood.data.repositories.IProvinceRepository;
import com.imaee.propinq.neighborhood.mappers.LocalityMapper;
import com.imaee.propinq.neighborhood.services.interfaces.ILocalityService;
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
public class LocalityService implements ILocalityService {
    private ILocalityRepository localityRepository;

    private IProvinceRepository provinceRepository;

    @Override
    public ResponseEntity<Void> createLocality(LocalityRequest localityRequest) {
        final Province localityRequestProvince = getLocalityProvince(localityRequest);
        throwExceptionIfLocalityExistsByNameAndProvince(localityRequest.name(), localityRequestProvince);

        final var locality = LocalityMapper.toLocality(
                localityRequest.name(),
                localityRequestProvince
        );

        localityRepository.save(locality);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<LocalityResponse>> getLocalities() {
        return ResponseEntity.ok(
                localityRepository
                        .findAll()
                        .stream()
                        .map(LocalityMapper::toLocalityResponse)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<LocalityResponse> getLocality(UUID id) {
        return ResponseEntity.ok(LocalityMapper.toLocalityResponse(findByIdOrThrowException(id)));
    }

    @Override
    public  ResponseEntity<Void> updateLocality(UUID id, LocalityRequest localityRequest) {
        Locality locality = findByIdOrThrowException(id);

        final Province localityRequestProvince = getLocalityProvince(localityRequest);
        throwExceptionIfLocalityExistsByNameAndProvince(localityRequest.name(), localityRequestProvince);

        locality.setName(localityRequest.name());
        locality.setProvince(localityRequestProvince);

        localityRepository.save(locality);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteLocality(UUID id) {
        localityRepository.delete(findByIdOrThrowException(id));

        return ResponseEntity.ok().build();
    }

    private Province getLocalityProvince(LocalityRequest localityRequest) {
        Optional<Province> localityProvince = provinceRepository.findById(localityRequest.provinceId());
        if (localityProvince.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, IProvinceRepository.MSG_NOT_EXISTS);
        }
        return localityProvince.get();
    }

    private void throwExceptionIfLocalityExistsByNameAndProvince(String name, Province province) {
        if (localityRepository.existsByNameIgnoreCaseAndProvince(name, province)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ILocalityRepository.MSG_ALREADY_EXISTS);
        }
    }

    private Locality findByIdOrThrowException(UUID id) {
        Optional<Locality> existingLocality = localityRepository.findById(id);
        if (existingLocality.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ILocalityRepository.MSG_NOT_EXISTS);
        }
        return existingLocality.get();
    }
}
