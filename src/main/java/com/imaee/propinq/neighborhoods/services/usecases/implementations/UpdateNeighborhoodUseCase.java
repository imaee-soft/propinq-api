package com.imaee.propinq.neighborhoods.services.usecases.implementations;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhoods.data.models.Neighborhood;
import com.imaee.propinq.neighborhoods.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.ICreateNeighborhoodUseCase;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.IGetNeighborhoodUseCase;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.IUpdateNeighborhoodUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.neighborhoods.utils.Constants.MSG_IS_DELETED;

@Component
@AllArgsConstructor
public class UpdateNeighborhoodUseCase implements IUpdateNeighborhoodUseCase {

    private final INeighborhoodRepository neighborhoodRepository;
    private final IGetNeighborhoodUseCase getNeighborhoodUseCase;
    private final ICreateNeighborhoodUseCase createNeighborhoodUseCase;

    @Override
    public void updateNeighborhood(NeighborhoodRequest neighborhoodRequest, UUID neighborhoodId) {
        Neighborhood neighborhood = getNeighborhoodUseCase.findNeighborhoodByIdOrThrowException(neighborhoodId);
        throwExceptionIfNeighborhoodIsDeleted(neighborhood);

        Locality neighborhoodRequestLocality = createNeighborhoodUseCase.getNeighborhoodLocality(neighborhoodRequest);
        createNeighborhoodUseCase.throwExceptionIfNeighborhoodExistsByNameAndLocality(neighborhoodRequest.name(), neighborhoodRequestLocality);

        neighborhood.setName(neighborhoodRequest.name());
        neighborhood.setLocality(neighborhoodRequestLocality);

        neighborhoodRepository.save(neighborhood);
    }

    @Override
    public void throwExceptionIfNeighborhoodIsDeleted(Neighborhood neighborhood) {
        if (neighborhood.isDeleted())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_IS_DELETED);
    }

}
