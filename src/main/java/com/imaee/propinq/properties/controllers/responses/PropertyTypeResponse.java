package com.imaee.propinq.properties.controllers.responses;

import java.util.UUID;

public record PropertyTypeResponse(
        UUID propertyTypeId,
        String name,
        String description,
        boolean deleted
) {
}
