package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IRestoreBuildingUseCase;
import com.imaee.propinq.shared.data.models.Image;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class RestoreBuildingUseCase implements IRestoreBuildingUseCase {

    private final IFindBuildingByIdUseCase findBuildingByIdUseCase;
    private final IBuildingRepository buildingRepository;

    @Override
    public BuildingDetailsResponse restoreBuilding(UUID buildingId) {
        Building building = findBuildingByIdUseCase.findBuilding(buildingId);
        building.setDeleted(false);
        List<String> imagesURLS = building.getImages().stream()
                .map(Image::getUrl)
                .toList();
        return BuildingMapper.toBuildingDetailsResponse(buildingRepository.save(building),
                imagesURLS);
    }
}
