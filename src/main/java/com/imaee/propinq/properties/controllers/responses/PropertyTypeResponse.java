package com.imaee.propinq.properties.controllers.responses;

import java.util.UUID;

public record PropertyTypeResponse(
        UUID id,
        String name,
        String description,
        boolean state
) {
}
