package com.imaee.propinq.neighborhoods.services.usecases.implementations;

import com.imaee.propinq.neighborhoods.data.models.Neighborhood;
import com.imaee.propinq.neighborhoods.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.IGetNeighborhoodUseCase;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.IRecoverNeighborhoodUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RecoverNeighborhoodUseCase implements IRecoverNeighborhoodUseCase {

    private final INeighborhoodRepository neighborhoodRepository;
    private final IGetNeighborhoodUseCase getNeighborhoodUseCase;
    @Override
    public void recoverNeighborhood(UUID neighborhoodId) {
        Neighborhood neighborhood = getNeighborhoodUseCase.findNeighborhoodByIdOrThrowException(neighborhoodId);
        neighborhood.setDeleted(false);
        neighborhoodRepository.save(neighborhood);

    }
}
