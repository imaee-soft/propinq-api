package com.imaee.propinq.buildings.builders;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;

import java.util.UUID;

/**
 * Test Data Builder for CreateBuildingRequest. Fixed defaults — no random values.
 */
public class CreateBuildingRequestBuilder {

    private static final String DEFAULT_NAME = "Torre Villa María";
    private static final String DEFAULT_DESCRIPTION = "Edificio de prueba para el frontend";
    private static final String DEFAULT_ADDRESS = "Bv. España 1000";
    private static final double DEFAULT_LATITUDE = -32.4094;
    private static final double DEFAULT_LONGITUDE = -63.2432;
    private static final String DEFAULT_TYPE = "EDIFICIO";

    private String name = DEFAULT_NAME;
    private String description = DEFAULT_DESCRIPTION;
    private String address = DEFAULT_ADDRESS;
    private Double latitude = DEFAULT_LATITUDE;
    private Double longitude = DEFAULT_LONGITUDE;
    private UUID userId;
    private String type = DEFAULT_TYPE;

    public static CreateBuildingRequestBuilder aCreateBuildingRequest() {
        return new CreateBuildingRequestBuilder();
    }

    public CreateBuildingRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateBuildingRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateBuildingRequestBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public CreateBuildingRequestBuilder withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public CreateBuildingRequestBuilder withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public CreateBuildingRequestBuilder withUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public CreateBuildingRequestBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public CreateBuildingRequest build() {
        return new CreateBuildingRequest(name, description, address, latitude, longitude, userId, type);
    }
}
