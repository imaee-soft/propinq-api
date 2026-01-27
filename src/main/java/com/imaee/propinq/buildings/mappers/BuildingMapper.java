package com.imaee.propinq.buildings.mappers;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.users.data.models.User;

import java.util.List;
import java.util.UUID;

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

    public static BuildingDetailsResponse toBuildingDetailsResponse(
            Building building,
            List<String> imagesURLS
    ) {
        return toBuildingDetailsResponse(building, imagesURLS, null);
    }

    public static BuildingDetailsResponse toBuildingDetailsResponse(
            Building building,
            List<String> imagesURLS,
            UUID favoriteId
    ) {
        return new BuildingDetailsResponse(
                building.getBuildingId().toString(),
                building.getName(),
                building.getDescription(),
                building.getAddress(),
                imagesURLS,
                building.getUser().getUserId(),
                building.getUser().getFullName(),
                building.getBuildingType().name(),
                building.isDeleted(),
                building.getLatitude(),
                building.getLongitude(),
                favoriteId,
                building.getCreatedAt()
        );
    }

    public static Building toBuilding(
            CreateBuildingRequest createBuildingRequest,
            User user,
            List<Image> images
    ) {
        return Building.builder()
                .name(createBuildingRequest.name())
                .description(createBuildingRequest.description())
                .address(createBuildingRequest.address())
                .latitude(createBuildingRequest.latitude())
                .longitude(createBuildingRequest.longitude())
                .user(user)
                .images(images)
                .buildingType(valueOf(createBuildingRequest.type()))
                .build();
    }

    public static Building toBuilding(
            UpdateBuildingRequest updateBuildingRequest,
            List<Image> images
    ) {
        return Building.builder()
                .name(updateBuildingRequest.name())
                .description(updateBuildingRequest.description())
                .images(images)
                .buildingType(valueOf(updateBuildingRequest.type()))
                .build();
    }
}