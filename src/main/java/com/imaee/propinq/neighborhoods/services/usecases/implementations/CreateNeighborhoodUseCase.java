package com.imaee.propinq.neighborhoods.services.usecases.implementations;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.localities.services.usecases.interfaces.IGetLocalityUseCase;
import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhoods.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhoods.mappers.NeighborhoodMapper;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.ICreateNeighborhoodUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.imaee.propinq.neighborhoods.utils.Constants.MSG_NOT_EXISTS;
import static com.imaee.propinq.neighborhoods.utils.Constants.MSG_ALREADY_EXISTS;


@Component
@AllArgsConstructor
public class CreateNeighborhoodUseCase implements ICreateNeighborhoodUseCase {

    private final INeighborhoodRepository neighborhoodRepository;
    private final IGetLocalityUseCase getLocalityUseCase;

    @Override
    public void createNeighborhood(NeighborhoodRequest neighborhoodRequest) {
        Locality neighborhoodRequestLocality = getNeighborhoodLocality(neighborhoodRequest);
        throwExceptionIfNeighborhoodExistsByNameAndLocality(neighborhoodRequest.name(), neighborhoodRequestLocality);

        final var neighborhood = NeighborhoodMapper.toNeighborhood(
                neighborhoodRequest.name(),
                neighborhoodRequestLocality,
                false,
                neighborhoodRequest.latitude(),
                neighborhoodRequest.longitude()
        );

        neighborhoodRepository.save(neighborhood);
    }


    @Override
    public Locality getNeighborhoodLocality(NeighborhoodRequest neighborhoodRequest) {
        Optional<Locality> neighborhoodLocality = Optional.ofNullable(getLocalityUseCase.findLocalityByIdOrThrowException(neighborhoodRequest.localityId()));
        if (neighborhoodLocality.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_NOT_EXISTS);
        }
        return neighborhoodLocality.get();
    }

    @Override
    public void throwExceptionIfNeighborhoodExistsByNameAndLocality(String name, Locality locality) {
        if (neighborhoodRepository.existsByNameIgnoreCaseAndLocality(name, locality)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_ALREADY_EXISTS);
        }
    }
}
