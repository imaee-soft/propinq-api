package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.services.facades.interfaces.IBuildingFacade;
import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;
import static com.imaee.propinq.buildings.mappers.BuildingMapper.toBuildingDetailsResponse;

@Component
@AllArgsConstructor
public class GetBuildingUseCase implements IGetBuildingUseCase {

    private final IFindBuildingByIdUseCase findBuildingByIdUseCase;
    private final IBuildingFacade buildingFacade;

    @Override
    public BuildingDetailsResponse getBuilding(UUID buildingId) {
        final var building = findBuildingByIdUseCase.findBuilding(buildingId);
        final var imagesURLS = buildingFacade.getImagesURLs(building);
        return toBuildingDetailsResponse(building, imagesURLS);
    }
}