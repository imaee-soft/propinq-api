package com.imaee.propinq.properties.builders;

import com.imaee.propinq.buildings.data.enums.BuildingType;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.users.data.models.User;

import java.util.ArrayList;

/**
 * Test Data Builder for Building entity in property integration tests.
 */
public class BuildingEntityBuilder {

    private static final String DEFAULT_NAME = "Test Building";
    private static final String DEFAULT_DESCRIPTION = "";
    private static final String DEFAULT_ADDRESS = "Address 123";
    private static final double DEFAULT_LATITUDE = 0.0;
    private static final double DEFAULT_LONGITUDE = 0.0;

    private String name = DEFAULT_NAME;
    private String description = DEFAULT_DESCRIPTION;
    private String address = DEFAULT_ADDRESS;
    private Double latitude = DEFAULT_LATITUDE;
    private Double longitude = DEFAULT_LONGITUDE;
    private User user;
    private BuildingType buildingType = BuildingType.EDIFICIO;

    public static BuildingEntityBuilder aBuilding() {
        return new BuildingEntityBuilder();
    }

    public BuildingEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public BuildingEntityBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public Building build() {
        return Building.builder()
                .name(name)
                .description(description)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .user(user)
                .images(new ArrayList<>())
                .buildingType(buildingType)
                .build();
    }
}
