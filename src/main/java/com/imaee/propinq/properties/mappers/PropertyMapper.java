package com.imaee.propinq.properties.mappers;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.users.data.models.User;

import java.util.List;
import java.util.UUID;

import static com.imaee.propinq.properties.data.enums.PropertyType.APARTAMENTO;
import static com.imaee.propinq.properties.data.enums.PropertyType.CASA;

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
                property.isDeleted(),
                property.getUser().getFullName(),
                property.getUser().getUserId()
        );
    }

    public static Property toApartment(CreatePropertyRequest request, List<Image> images, Building building) {
        final var apartment = toBasicProperty(request, images);
        apartment.setPropertyType(APARTAMENTO);
        apartment.setBuilding(building);
        apartment.setUser(building.getUser());
        apartment.setTitle(buildApartmentName(request.number(), request.floor()));
        apartment.setApartmentNumber(request.number());
        return apartment;
    }

    private static String buildApartmentName(String number, int floor) {
        return "Dpto. " + number + ", Piso " + floor;
    }

    public static Property toHouse(CreatePropertyRequest request, List<Image> images, User user) {
        final var house = toBasicProperty(request, images);
        house.setPropertyType(CASA);
        house.setUser(user);
        house.setTitle(buildHouseName(request.address()));
        house.setLatitude(request.latitude());
        house.setLongitude(request.longitude());
        return house;
    }

    private static String buildHouseName(String address) {
        return "Vivienda ubicada en " + address;
    }

    private static Property toBasicProperty(CreatePropertyRequest request, List<Image> images) {
        return Property.builder()
                .address(request.address())
                .price(request.price())
                .description(request.description())
                .bedrooms(request.bedrooms())
                .bathrooms(request.bathrooms())
                .petsAllowed(request.petsAllowed())
                .furnishing(request.hasFurniture())
                .expenses(request.paysExpenses())
                .images(images)
                .build();
    }
}