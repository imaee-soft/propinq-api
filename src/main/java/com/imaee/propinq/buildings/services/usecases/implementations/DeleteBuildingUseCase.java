package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.services.usecases.interfaces.IDeleteBuildingUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
@AllArgsConstructor
public class DeleteBuildingUseCase implements IDeleteBuildingUseCase {

    private final IBuildingRepository buildingRepository;
    private final IFindBuildingByIdUseCase findBuildingByIdUseCase;

    @Override
    public void deleteBuilding(UUID buildingId) {
        Building building = findBuildingByIdUseCase.findBuilding(buildingId);
        throwExceptionIfBuildingHasProperties(building);
        building.setDeleted(true);
        buildingRepository.save(building);
    }

    private void throwExceptionIfBuildingHasProperties(Building building) {
        if(building.getProperties() != null && !building.getProperties().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El Inmueble posee propiedades, por lo tanto, no puede eliminarse.");
        }
    }
}
