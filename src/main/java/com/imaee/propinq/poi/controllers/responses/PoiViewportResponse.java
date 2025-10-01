package com.imaee.propinq.poi.controllers.responses;

import com.imaee.propinq.poi.data.enums.PoiType;

import java.util.UUID;

public record PoiViewportResponse(
        UUID id,
        PoiType type,
        String name,
        double latitude,
        double longitude
) {}
