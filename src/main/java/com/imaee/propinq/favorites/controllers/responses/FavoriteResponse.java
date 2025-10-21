package com.imaee.propinq.favorites.controllers.responses;


import java.util.UUID;

public record FavoriteResponse(
        UUID favoriteID,
        UUID userID,
        UUID propertyID,
        UUID buildingID

) {}