package com.imaee.propinq.buildings.controllers.responses;

import com.imaee.propinq.properties.controllers.responses.PropertyResponse;

import java.util.List;
import java.util.UUID;

public record BuildingWithPropertiesResponse(
        UUID buildingId,
        Double latitude,
        Double longitude,
        List<PropertyResponse> properties
) {}
