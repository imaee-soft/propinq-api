package com.imaee.propinq.properties.builders;

import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;

import java.util.UUID;

/**
 * Test Data Builder for CreatePropertyRequest. Fixed defaults — no random values.
 */
public class CreatePropertyRequestBuilder {

    private static final String DEFAULT_DESCRIPTION = "Vivienda de prueba para integración";
    private static final double DEFAULT_PRICE = 150_000.0;
    private static final int DEFAULT_BEDROOMS = 2;
    private static final int DEFAULT_BATHROOMS = 1;
    private static final String DEFAULT_ADDRESS = "Av. San Martín 1200";
    private static final double DEFAULT_LATITUDE = -32.4100;
    private static final double DEFAULT_LONGITUDE = -63.2400;

    private String description = DEFAULT_DESCRIPTION;
    private Double price = DEFAULT_PRICE;
    private Integer bedrooms = DEFAULT_BEDROOMS;
    private Integer bathrooms = DEFAULT_BATHROOMS;
    private Boolean petsAllowed = true;
    private Boolean hasFurniture = false;
    private Boolean paysExpenses = true;
    private String type = "APARTAMENTO";
    private Integer floor = 3;
    private String number = "B";
    private String address = DEFAULT_ADDRESS;
    private Double latitude = DEFAULT_LATITUDE;
    private Double longitude = DEFAULT_LONGITUDE;
    private UUID buildingId;
    private UUID userId;

    public static CreatePropertyRequestBuilder aCreatePropertyRequest() {
        return new CreatePropertyRequestBuilder();
    }

    public CreatePropertyRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CreatePropertyRequestBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public CreatePropertyRequestBuilder withBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public CreatePropertyRequestBuilder withBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public CreatePropertyRequestBuilder withPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
        return this;
    }

    public CreatePropertyRequestBuilder withHasFurniture(Boolean hasFurniture) {
        this.hasFurniture = hasFurniture;
        return this;
    }

    public CreatePropertyRequestBuilder withPaysExpenses(Boolean paysExpenses) {
        this.paysExpenses = paysExpenses;
        return this;
    }

    public CreatePropertyRequestBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public CreatePropertyRequestBuilder withFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public CreatePropertyRequestBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public CreatePropertyRequestBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public CreatePropertyRequestBuilder withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public CreatePropertyRequestBuilder withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public CreatePropertyRequestBuilder withBuildingId(UUID buildingId) {
        this.buildingId = buildingId;
        return this;
    }

    public CreatePropertyRequestBuilder withUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public CreatePropertyRequest build() {
        return new CreatePropertyRequest(
                description, price, bedrooms, bathrooms,
                petsAllowed, hasFurniture, paysExpenses, type,
                floor, number, address, latitude, longitude,
                buildingId, userId
        );
    }
}
