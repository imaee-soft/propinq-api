package com.imaee.propinq.buildings.mappers;


import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.models.BuildingType;
import com.imaee.propinq.shared.data.models.Review;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.users.data.models.User;

import java.util.List;
import java.util.UUID;

public class BuildingMapper {

    private BuildingMapper() {}

    public static BuildingResponse toBuildingResponse(Building building) {
        return new BuildingResponse(
                building.getBuildingId(),
                building.getLatitude(),
                building.getLongitude()
        );
    }

    public static BuildingDetailsResponse toBuildingDetailsResponse(Building building, List<String>imagesURLS, UUID userId, String userFullName, String buildingTypeName) {
        return new BuildingDetailsResponse(
                building.getName(),
                building.getDescription(),
                building.getAddress(),
                imagesURLS,
                userId,
                userFullName,
                buildingTypeName
        );
    }

    public static Building toBuilding(UUID buildingId, String description, String address, List<Image> images, List<Property> properties,
                                      User user, BuildingType buildingType, List<Review> reviews) {
        return Building.builder()
                .buildingId(buildingId)
                .description(description)
                .address(address)
                .images(images)
                .properties(properties)
                .user(user)
                .buildingType(buildingType)
                .build();
    }

}
