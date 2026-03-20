package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.services.usecases.interfaces.IDeleteBuildingUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.buildings.Constants.CANNOT_DELETE_BUILDING_WITH_PROPERTIES_MESSAGE;

@Component
@AllArgsConstructor
public class DeleteBuildingUseCase implements IDeleteBuildingUseCase {

    private final IBuildingRepository buildingRepository;

    @Override
    public void deleteBuilding(UUID buildingId) {
        Building building = buildingRepository.findByIdWithProperties(buildingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Building not found"));
        throwExceptionIfBuildingHasProperties(building);
        building.setDeleted(true);
        buildingRepository.save(building);
    }

    private void throwExceptionIfBuildingHasProperties(Building building) {
        if(building.getProperties() != null && !building.getProperties().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANNOT_DELETE_BUILDING_WITH_PROPERTIES_MESSAGE);
        }
    }
}
