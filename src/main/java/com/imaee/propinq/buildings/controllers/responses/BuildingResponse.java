package com.imaee.propinq.buildings.controllers.responses;

import java.util.UUID;

public record BuildingResponse(
        UUID buildingId,
        Double latitude,
        Double longitude,
        String name
) {}