package com.imaee.propinq.neighborhoods.controllers.responses;

import java.util.UUID;

public record NeighborhoodResponse(
        UUID neighborhoodId,
        String name,
        UUID localityId,
        Boolean deleted,
        Double latitude,
        Double longitude
) {
}
