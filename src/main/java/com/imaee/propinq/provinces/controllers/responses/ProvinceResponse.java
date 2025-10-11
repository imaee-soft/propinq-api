package com.imaee.propinq.provinces.controllers.responses;

import java.util.UUID;

public record ProvinceResponse(
        UUID id,
        String name,
        Double latitude,
        Double longitude
) {
}