package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IRestoreBuildingUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;

import static com.imaee.propinq.buildings.Constants.BUILDING_IS_NOT_DELETED_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class RestoreBuildingUseCase implements IRestoreBuildingUseCase {

    private final IFindBuildingByIdUseCase findBuildingByIdUseCase;
    private final IBuildingRepository buildingRepository;

    @Override
    public void restoreBuilding(UUID buildingId) {
        Building building = findBuildingByIdUseCase.findBuilding(buildingId);
        throwExceptionIfBuildingIsNotDeleted(building);
        building.setDeleted(false);
        buildingRepository.save(building);
    }

    private void throwExceptionIfBuildingIsNotDeleted(Building building) {
        if (!building.isDeleted())
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    BUILDING_IS_NOT_DELETED_MESSAGE
            );
    }
}
