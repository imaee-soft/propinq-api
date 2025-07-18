package com.imaee.propinq.buildings.mappers;

import com.imaee.propinq.buildings.controllers.requests.BuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.users.data.models.User;

import java.util.List;

import static com.imaee.propinq.buildings.data.enums.BuildingType.valueOf;

public class BuildingMapper {

    private BuildingMapper() {}

    public static BuildingResponse toBuildingResponse(Building building) {
        return new BuildingResponse(
                building.getBuildingId(),
                building.getLatitude(),
                building.getLongitude()
        );
    }

    public static BuildingDetailsResponse toBuildingDetailsResponse(Building building, List<String> imagesURLS) {
        return new BuildingDetailsResponse(
                building.getName(),
                building.getDescription(),
                building.getAddress(),
                imagesURLS,
                building.getUser().getUserId(),
                building.getUser().getFullName(),
                building.getBuildingType().name()
        );
    }

    public static Building toBuilding(
            BuildingRequest buildingRequest,
            User user,
            List<Image> images
    ) {
        return Building.builder()
                .name(buildingRequest.name())
                .description(buildingRequest.description())
                .address(buildingRequest.address())
                .latitude(buildingRequest.latitude())
                .longitude(buildingRequest.longitude())
                .user(user)
                .images(images)
                .buildingType(valueOf(buildingRequest.type()))
                .build();
    }
}