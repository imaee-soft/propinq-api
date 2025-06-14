package com.imaee.propinq.properties.controllers.responses;

import java.util.List;
import java.util.UUID;

public record PropertyResponse (
        UUID propertyId,
        String address,
        UUID buildingId,
        List<String> imagesURL,
        List<UUID> reviews
){
}
