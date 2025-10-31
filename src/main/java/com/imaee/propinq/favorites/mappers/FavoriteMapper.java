package com.imaee.propinq.favorites.mappers;

import com.imaee.propinq.favorites.controllers.responses.FavoriteResponse;
import com.imaee.propinq.favorites.data.models.Favorite;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavoriteMapper {
    public FavoriteResponse toResponse(Favorite favorite) {
        if (favorite == null) return null;

        return new FavoriteResponse(
                favorite.getFavoriteId(),
                favorite.getUserID() != null ? favorite.getUserID().getUserId() : null,
                favorite.getPropertyID() != null ? favorite.getPropertyID().getPropertyId() : null,
                favorite.getBuildingID() != null ? favorite.getBuildingID().getBuildingId() : null
        );
    }

    public List<FavoriteResponse> toResponseList(List<Favorite> favorites) {
        return favorites.stream()
                .map(this::toResponse)
                .toList();
    }
}
