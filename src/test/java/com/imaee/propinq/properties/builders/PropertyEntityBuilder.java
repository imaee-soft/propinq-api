package com.imaee.propinq.properties.builders;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.properties.data.enums.PropertyType;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.users.data.models.User;

import java.util.ArrayList;

/**
 * Test Data Builder for Property entity in get-properties integration tests.
 */
public class PropertyEntityBuilder {

    private static final String DEFAULT_ADDRESS = "Av. Test 100";
    private static final double DEFAULT_PRICE = 100_000.0;
    private static final String DEFAULT_DESCRIPTION = "Desc";
    private static final String DEFAULT_TITLE = "Title";
    private static final int DEFAULT_BEDROOMS = 1;
    private static final int DEFAULT_BATHROOMS = 1;
    private static final double DEFAULT_LATITUDE = -32.41;
    private static final double DEFAULT_LONGITUDE = -63.24;

    private String address = DEFAULT_ADDRESS;
    private Building building = null;
    private PropertyType propertyType = PropertyType.CASA;
    private Double price = DEFAULT_PRICE;
    private String description = DEFAULT_DESCRIPTION;
    private String title = DEFAULT_TITLE;
    private Integer floor = null;
    private Integer bedrooms = DEFAULT_BEDROOMS;
    private Integer bathrooms = DEFAULT_BATHROOMS;
    private boolean petsAllowed = false;
    private boolean furnishing = false;
    private boolean expenses = false;
    private String apartmentNumber = null;
    private User user;
    private boolean deleted = false;
    private Double latitude = DEFAULT_LATITUDE;
    private Double longitude = DEFAULT_LONGITUDE;

    public static PropertyEntityBuilder aProperty() {
        return new PropertyEntityBuilder();
    }

    public PropertyEntityBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public PropertyEntityBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public PropertyEntityBuilder withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public PropertyEntityBuilder withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public PropertyEntityBuilder withDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public Property build() {
        Property p = Property.builder()
                .address(address)
                .building(building)
                .propertyType(propertyType)
                .price(price)
                .description(description)
                .title(title)
                .floor(floor)
                .bedrooms(bedrooms)
                .bathrooms(bathrooms)
                .petsAllowed(petsAllowed)
                .furnishing(furnishing)
                .expenses(expenses)
                .apartmentNumber(apartmentNumber)
                .user(user)
                .deleted(deleted)
                .images(new ArrayList<>())
                .build();
        p.setLatitude(latitude);
        p.setLongitude(longitude);
        return p;
    }
}
