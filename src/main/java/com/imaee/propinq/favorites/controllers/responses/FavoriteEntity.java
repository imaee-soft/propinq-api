package com.imaee.propinq.favorites.controllers.responses;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record FavoriteEntity(
        UUID favoriteId,
        UUID entityId,
        String type,
        String title,
        String ownerName,
        LocalDateTime favoriteDate,
        Double latitude,
        Double longitude
) {}