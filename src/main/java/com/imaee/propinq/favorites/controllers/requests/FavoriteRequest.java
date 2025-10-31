package com.imaee.propinq.favorites.controllers.requests;

import lombok.Data;

import java.util.UUID;

public record FavoriteRequest(
        UUID userID,
        UUID buildingID,
        UUID propertyID
) {}