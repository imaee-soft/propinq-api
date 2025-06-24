package com.imaee.propinq.neighborhood.controllers.responses;

import java.util.UUID;

public record NeighborhoodResponse(
        UUID id,
        String name,
        UUID localityId
) {
}
