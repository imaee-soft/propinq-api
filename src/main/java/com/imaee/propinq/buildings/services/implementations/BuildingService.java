package com.imaee.propinq.buildings.services.implementations;

import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.interfaces.IBuildingService;
import com.imaee.propinq.shared.data.models.Image;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BuildingService implements IBuildingService {

    private static final String NO_BUILDING_MESSAGE = "Building does not exist";
    private final IBuildingRepository buildingRepository;


    @Override
    public List<BuildingResponse> getBuildings() {
        return buildingRepository.findAll()
                .stream()
                .map(BuildingMapper::toBuildingResponse)
                .toList();
    }

    @Override
    public BuildingDetailsResponse getBuilding(UUID buildingId) {
        Building building = findBuildingByIdOrThrowException(buildingId);
        List<String> imagesURLS = getImagesURLs(building);
        UUID userId = building.getUser().getUserId();
        String userFullName = building.getUser().getFirstName() + " " + building.getUser().getLastName();
        String buildingTypeName = building.getBuildingType().getName();
       return BuildingMapper.toBuildingDetailsResponse(building, imagesURLS, userId, userFullName, buildingTypeName);
    }

    private List<String> getImagesURLs(Building building) {
        return building.getImages().stream()
                .map(Image::getUrl)
                .toList();
    }

    private Building findBuildingByIdOrThrowException(UUID buildingId) {
        return buildingRepository.findById(buildingId)
                .orElseThrow(() -> new NoSuchElementException(NO_BUILDING_MESSAGE));
    }
}
