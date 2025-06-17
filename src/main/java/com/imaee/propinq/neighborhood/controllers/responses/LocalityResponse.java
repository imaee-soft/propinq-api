package com.imaee.propinq.neighborhood.controllers.responses;

import java.util.UUID;

public record LocalityResponse(
        UUID id,
        String name
) {
}
