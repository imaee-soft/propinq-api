package com.imaee.propinq.neighborhoods.builders;

import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;

import java.util.UUID;

/**
 * Test Data Builder for NeighborhoodRequest. Use in unit tests only.
 * Fixed defaults — no random values.
 */
public class NeighborhoodRequestBuilder {

    private static final UUID DEFAULT_LOCALITY_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final String DEFAULT_NAME = "Default Neighborhood";
    private static final Double DEFAULT_LATITUDE = 12.34;
    private static final Double DEFAULT_LONGITUDE = 56.78;

    private String name = DEFAULT_NAME;
    private UUID localityId = DEFAULT_LOCALITY_ID;
    private Double latitude = DEFAULT_LATITUDE;
    private Double longitude = DEFAULT_LONGITUDE;

    public static NeighborhoodRequestBuilder aNeighborhoodRequest() {
        return new NeighborhoodRequestBuilder();
    }

    public NeighborhoodRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public NeighborhoodRequestBuilder withLocalityId(UUID localityId) {
        this.localityId = localityId;
        return this;
    }

    public NeighborhoodRequestBuilder withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public NeighborhoodRequestBuilder withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public NeighborhoodRequest build() {
        return new NeighborhoodRequest(name, localityId, latitude, longitude);
    }
}
