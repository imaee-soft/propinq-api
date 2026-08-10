package com.imaee.propinq.neighborhoods.builders;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.neighborhoods.data.models.Neighborhood;

import java.util.UUID;

/**
 * Test Data Builder for Neighborhood. Use in unit tests only.
 * Fixed defaults — no random values.
 */
public class NeighborhoodBuilder {

    private static final UUID DEFAULT_NEIGHBORHOOD_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final String DEFAULT_NAME = "Default Neighborhood";
    private static final Double DEFAULT_LATITUDE = 12.34;
    private static final Double DEFAULT_LONGITUDE = 56.78;

    private UUID neighborhoodId = DEFAULT_NEIGHBORHOOD_ID;
    private String name = DEFAULT_NAME;
    private Locality locality;
    private boolean deleted = false;
    private Double latitude = DEFAULT_LATITUDE;
    private Double longitude = DEFAULT_LONGITUDE;

    public static NeighborhoodBuilder aNeighborhood() {
        return new NeighborhoodBuilder();
    }

    public NeighborhoodBuilder withNeighborhoodId(UUID neighborhoodId) {
        this.neighborhoodId = neighborhoodId;
        return this;
    }

    public NeighborhoodBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public NeighborhoodBuilder withLocality(Locality locality) {
        this.locality = locality;
        return this;
    }

    public NeighborhoodBuilder withDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public NeighborhoodBuilder withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public NeighborhoodBuilder withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Neighborhood build() {
        if (locality == null) {
            locality = LocalityBuilder.aLocality().build();
        }
        Neighborhood neighborhood = Neighborhood.builder()
                .neighborhoodId(neighborhoodId)
                .name(name)
                .locality(locality)
                .deleted(deleted)
                .build();
        neighborhood.setLatitude(latitude);
        neighborhood.setLongitude(longitude);
        return neighborhood;
    }
}
