package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingUseCase;
import com.imaee.propinq.shared.data.models.Image;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.imaee.propinq.buildings.mappers.BuildingMapper.toBuildingDetailsResponse;

@Component
@AllArgsConstructor
public class GetBuildingUseCase implements IGetBuildingUseCase {

    private final IFindBuildingByIdUseCase findBuildingByIdUseCase;

    @Override
    public BuildingDetailsResponse getBuilding(UUID buildingId) {
        final var building = findBuildingByIdUseCase.findBuilding(buildingId);
        final var imagesURLS = getImagesURLs(building);
        return toBuildingDetailsResponse(building, imagesURLS);
    }

    private List<String> getImagesURLs(Building building) {
        return building.getImages().stream()
                .map(Image::getUrl)
                .toList();
    }
}