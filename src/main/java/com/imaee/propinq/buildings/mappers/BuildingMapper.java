package com.imaee.propinq.buildings.mappers;

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
        List<String> imagesURLS = getImagesURLs(building);
        List<UUID> properties = getProperties(building);
        UUID userId = building.getUser().getUserId();
        UUID buildingTypeId = building.getBuildingType().getBuildingTypeId();
        List<UUID> reviews = getReviews(building);
        return new BuildingResponse(
                building.getBuildingId(),
                building.getName(),
                building.getDescription(),
                building.getAddress(),
                imagesURLS,
                userId,
                buildingTypeId
        );
    }
    private static List<String> getImagesURLs(Building building) {
        return building.getImages().stream()
                .map(image -> image.getUrl())
                .toList();
    }
    private static List<UUID> getProperties(Building building) {
        return building.getProperties().stream()
                .map(property -> property.getPropertyId())
                .toList();

    }
    private static List<UUID> getReviews(Building building) {
        return building.getReviews().stream()
                .map(Review::getReviewId)
                .toList();
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
                .reviews(reviews)
                .build();
    }

}
