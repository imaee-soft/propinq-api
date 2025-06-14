package com.imaee.propinq.buildings.controllers.responses;

import java.util.List;
import java.util.UUID;

public record BuildingResponse(
        UUID buildingId,
        String name,
        String description,
        String address,
        List<String> imagesURL,
        List<UUID> properties,
        UUID userId,
        UUID buildingTypeId,
        List<UUID> reviews
) {
}
