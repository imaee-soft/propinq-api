package com.imaee.propinq.buildings.controllers.responses;

import java.util.List;
import java.util.UUID;

public record BuildingDetailsResponse(
        String buildingId,
        String name,
        String description,
        String address,
        List<String> imagesURL,
        UUID userId,
        String userFullName,
        String buildingTypeName,
        boolean deleted,
        Double latitude,
        Double longitude
) {}