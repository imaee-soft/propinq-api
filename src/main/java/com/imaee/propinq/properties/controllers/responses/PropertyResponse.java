package com.imaee.propinq.properties.controllers.responses;

import java.util.UUID;

public record PropertyResponse (
        UUID propertyId,
        Double latitude,
        Double longitude,
        String title
) {}