package com.imaee.propinq.properties.mappers;


import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.shared.data.models.Image;

import java.util.List;
import java.util.UUID;

import static com.imaee.propinq.properties.data.enums.PropertyType.APARTAMENTO;

public class PropertyMapper {

    public static PropertyResponse toPropertyResponse(Property property) {
        return new PropertyResponse(
                property.getPropertyId(),
                property.getLatitude(),
                property.getLongitude()
        );
    }

    public static PropertyDetailsResponse toPropertyDetailsResponse(Property property, List<String> imagesURLS) {
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
                property.isFurnishing(),
                property.isExpenses(),
                property.getApartmentNumber(),
                property.isDeleted()
        );
    }

    public static Property toApartment(CreatePropertyRequest request, List<Image> images, Building building) {
        return Property.builder()
                .building(building)
                .propertyType(APARTAMENTO)
                .user(building.getUser())
                .address(building.getAddress())
                .images(images)
                .price(request.price())
                .description(request.description())
                .floor(request.floor())
                .bedrooms(request.bedrooms())
                .bathrooms(request.bathrooms())
                .petsAllowed(request.petsAllowed())
                .furnishing(request.hasFurniture())
                .expenses(request.paysExpenses())
                .apartmentNumber(request.number())
                .title(buildApartmentName(request.number(), request.floor()))
                .build();
    }

    private static String buildApartmentName(String number, int floor) {
        return "Dpto. " + number + ", Piso " + floor;
    }
}