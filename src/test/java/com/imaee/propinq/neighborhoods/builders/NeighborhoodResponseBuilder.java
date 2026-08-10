package com.imaee.propinq.neighborhoods.builders;

import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;

import java.util.UUID;

/**
 * Test Data Builder for NeighborhoodResponse. Use in unit tests only.
 * Fixed defaults — no random values.
 */
public class NeighborhoodResponseBuilder {

    private static final UUID DEFAULT_NEIGHBORHOOD_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID DEFAULT_LOCALITY_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final String DEFAULT_NAME = "Default Neighborhood";
    private static final Double DEFAULT_LATITUDE = 12.34;
    private static final Double DEFAULT_LONGITUDE = 56.78;

    private UUID neighborhoodId = DEFAULT_NEIGHBORHOOD_ID;
    private String name = DEFAULT_NAME;
    private UUID localityId = DEFAULT_LOCALITY_ID;
    private Boolean deleted = false;
    private Double latitude = DEFAULT_LATITUDE;
    private Double longitude = DEFAULT_LONGITUDE;

    public static NeighborhoodResponseBuilder aNeighborhoodResponse() {
        return new NeighborhoodResponseBuilder();
    }

    public NeighborhoodResponseBuilder withNeighborhoodId(UUID neighborhoodId) {
        this.neighborhoodId = neighborhoodId;
        return this;
    }

    public NeighborhoodResponseBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public NeighborhoodResponseBuilder withLocalityId(UUID localityId) {
        this.localityId = localityId;
        return this;
    }

    public NeighborhoodResponseBuilder withDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public NeighborhoodResponseBuilder withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public NeighborhoodResponseBuilder withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public NeighborhoodResponse build() {
        return new NeighborhoodResponse(neighborhoodId, name, localityId, deleted, latitude, longitude);
    }
}
