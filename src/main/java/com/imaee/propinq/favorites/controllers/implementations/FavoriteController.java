package com.imaee.propinq.favorites.controllers.implementations;

import com.imaee.propinq.favorites.controllers.interfaces.IFavoriteController;
import com.imaee.propinq.favorites.controllers.requests.FavoriteRequest;
import com.imaee.propinq.favorites.controllers.responses.FavoriteResponse;
import com.imaee.propinq.favorites.data.models.Favorite;
import com.imaee.propinq.favorites.mappers.FavoriteMapper;
import com.imaee.propinq.favorites.services.interfaces.IFavoriteService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FavoriteController implements IFavoriteController {
    private final IFavoriteService favoriteService;
    private final FavoriteMapper favoriteMapper;

    @Override
    public void addFavorite(FavoriteRequest request) {
        favoriteService.addFavorite(request.userID(), request.propertyID(), request.buildingID());
    }

    @Override
    public List<FavoriteResponse> getUserFavorites(UUID userID) {
        List<Favorite> favorites = favoriteService.getUserFavorites(userID);
        return favoriteMapper.toResponseList(favorites);
    }

    @Override
    public List<FavoriteResponse> getFavoritesByProperty(UUID userID) {
        List<Favorite> favorites = favoriteService.getFavoritesByProperty(userID);
        return favoriteMapper.toResponseList(favorites);
    }

    @Override
    public List<FavoriteResponse> getFavoritesByBuilding(UUID userID) {
        List<Favorite> favorites = favoriteService.getFavoritesByBuilding(userID);
        return favoriteMapper.toResponseList(favorites);
    }

    @Override
    public ResponseEntity<Void> removeFavorite(UUID favoriteID) {
        favoriteService.removeFavorite(favoriteID);
        return ResponseEntity.noContent().build();
    }


}
