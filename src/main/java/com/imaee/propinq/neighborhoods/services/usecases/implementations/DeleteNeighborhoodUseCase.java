package com.imaee.propinq.neighborhoods.services.usecases.implementations;

import com.imaee.propinq.neighborhoods.data.models.Neighborhood;
import com.imaee.propinq.neighborhoods.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.IDeleteNeighborhoodUseCase;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.IGetNeighborhoodUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class DeleteNeighborhoodUseCase implements IDeleteNeighborhoodUseCase {

    private final INeighborhoodRepository neighborhoodRepository;
    private final IGetNeighborhoodUseCase getNeighborhoodUseCase;
    private final UpdateNeighborhoodUseCase updateNeighborhood;

    @Override
    public void deleteNeighborhood(UUID neighborhoodId) {

        Neighborhood neighborhood = getNeighborhoodUseCase.findNeighborhoodByIdOrThrowException(neighborhoodId);
        updateNeighborhood.throwExceptionIfNeighborhoodIsDeleted(neighborhood);

        neighborhood.setDeleted(true);

        neighborhoodRepository.save(neighborhood);
    }
}
