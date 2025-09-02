package com.imaee.propinq.properties.mappers;


import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.data.models.Property;

import java.util.List;
import java.util.UUID;

public class PropertyMapper {

    public static PropertyResponse toPropertyResponse(Property property) {
        return new PropertyResponse(
                property.getPropertyId(),
                property.getLatitude(),
                property.getLongitude()
        );
    }

    public static PropertyDetailsResponse toPropertyDetailsResponse(Property property, List<String> imagesURLS){
        UUID buildingId = property.getBuilding() != null ? property.getBuilding().getBuildingId() : null;
        return new PropertyDetailsResponse(
                property.getPropertyId(),
                property.getAddress(),
                buildingId,
                imagesURLS,
                property.getPrice(),
                property.getDescription(),
                property.getTitle(),
                property.getFloor(),
                property.getBedrooms(),
                property.getBathrooms(),
                property.isPetsAllowed(),
                property.getArea(),
                property.getApartmentNumber(),
                property.isDeleted()
        );
    }
}
