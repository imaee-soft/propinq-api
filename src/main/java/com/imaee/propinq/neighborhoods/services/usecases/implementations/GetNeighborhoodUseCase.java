package com.imaee.propinq.neighborhoods.services.usecases.implementations;

import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.data.models.Neighborhood;
import com.imaee.propinq.neighborhoods.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhoods.mappers.NeighborhoodMapper;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.IGetNeighborhoodUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static com.imaee.propinq.neighborhoods.utils.Constants.MSG_NOT_EXISTS;

@Component
@AllArgsConstructor
public class GetNeighborhoodUseCase implements IGetNeighborhoodUseCase {

    private final INeighborhoodRepository neighborhoodRepository;

    @Override
    public NeighborhoodResponse getNeighborhood(UUID neighborhoodId) {
        return NeighborhoodMapper.toNeighborhoodResponse(findNeighborhoodByIdOrThrowException(neighborhoodId));
    }


    @Override
    public Neighborhood findNeighborhoodByIdOrThrowException(UUID id) {
        Optional<Neighborhood> existingNeighborhood = neighborhoodRepository.findById(id);
        if (existingNeighborhood.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_NOT_EXISTS);
        }
        return existingNeighborhood.get();
    }
}
