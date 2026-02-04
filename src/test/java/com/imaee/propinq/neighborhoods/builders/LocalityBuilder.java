package com.imaee.propinq.neighborhoods.builders;

import com.imaee.propinq.localities.data.models.Locality;

import java.util.UUID;

/**
 * Test Data Builder for Locality. Use in unit tests only.
 * Fixed defaults — no random values.
 */
public class LocalityBuilder {

    private static final UUID DEFAULT_LOCALITY_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final String DEFAULT_NAME = "Default Locality";

    private UUID localityId = DEFAULT_LOCALITY_ID;
    private String name = DEFAULT_NAME;
    private Double latitude = 0.0;
    private Double longitude = 0.0;
    private boolean deleted = false;

    public static LocalityBuilder aLocality() {
        return new LocalityBuilder();
    }

    public LocalityBuilder withLocalityId(UUID localityId) {
        this.localityId = localityId;
        return this;
    }

    public LocalityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public LocalityBuilder withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public LocalityBuilder withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public LocalityBuilder withDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public Locality build() {
        Locality locality = Locality.builder()
                .localityId(localityId)
                .name(name)
                .deleted(deleted)
                .build();
        locality.setLatitude(latitude);
        locality.setLongitude(longitude);
        return locality;
    }
}
