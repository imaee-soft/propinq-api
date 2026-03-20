package com.imaee.propinq.favorites.mappers;

import com.imaee.propinq.favorites.controllers.responses.FavoriteEntity;
import com.imaee.propinq.favorites.controllers.responses.FavoriteResponse;
import com.imaee.propinq.favorites.data.models.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavoriteMapper {

    private static final String BUILDING_KEY = "building";
    private static final String PROPERTY_KEY = "property";

    public FavoriteResponse toResponse(Favorite favorite) {
        if (favorite == null) return null;
        return new FavoriteResponse(
                favorite.getFavoriteId(),
                favorite.getUserID() != null ? favorite.getUserID().getUserId() : null,
                favorite.getPropertyID() != null ? favorite.getPropertyID().getPropertyId() : null,
                favorite.getBuildingID() != null ? favorite.getBuildingID().getBuildingId() : null
        );
    }

    public FavoriteEntity toBuilding(Favorite favorite) {
        final var building = favorite.getBuildingID();
        return FavoriteEntity.builder()
                .favoriteId(favorite.getFavoriteId())
                .entityId(building.getBuildingId())
                .type(BUILDING_KEY)
                .title(building.getName())
                .ownerName(building.getUser().getFullName())
                .favoriteDate(favorite.getFavoriteDate())
                .longitude(building.getLongitude())
                .latitude(building.getLatitude())
                .build();
    }

    public FavoriteEntity toProperty(Favorite favorite) {
        final var property = favorite.getPropertyID();
        return FavoriteEntity.builder()
                .favoriteId(favorite.getFavoriteId())
                .entityId(property.getPropertyId())
                .type(PROPERTY_KEY)
                .title(property.getTitle())
                .ownerName(property.getUser().getFullName())
                .favoriteDate(favorite.getFavoriteDate())
                .longitude(property.getLongitude())
                .latitude(property.getLatitude())
                .build();
    }
}