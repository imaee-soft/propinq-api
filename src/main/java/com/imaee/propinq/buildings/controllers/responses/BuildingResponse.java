package com.imaee.propinq.buildings.controllers.responses;

import com.imaee.propinq.shared.data.models.Locatable;

import java.util.UUID;

public record BuildingResponse(
        UUID buildingId,
        Double latitude,
        Double longitude
) {
}
