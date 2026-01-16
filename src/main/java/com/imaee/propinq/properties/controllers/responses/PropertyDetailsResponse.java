package com.imaee.propinq.properties.controllers.responses;

import java.util.List;
import java.util.UUID;

public record PropertyDetailsResponse (
        UUID propertyId,
        String address,
        UUID buildingId,
        List<String> imagesURL,
        Double price,
        String description,
        String title,
        Integer floor,
        Integer bedrooms,
        Integer bathrooms,
        boolean petsAllowed,
        boolean furnishing,
        boolean expenses,
        String apartmentNumber,
        boolean deleted,
        String ownerFullName,
        UUID ownerId,
        Double latitude,
        Double longitude,
        boolean isFavorite,
        boolean isContacted
){}