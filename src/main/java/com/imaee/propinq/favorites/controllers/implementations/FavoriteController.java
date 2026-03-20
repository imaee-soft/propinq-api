package com.imaee.propinq.favorites.controllers.implementations;

import com.imaee.propinq.favorites.controllers.interfaces.IFavoriteController;
import com.imaee.propinq.favorites.controllers.requests.FavoriteRequest;
import com.imaee.propinq.favorites.controllers.responses.FavoriteEntity;
import com.imaee.propinq.favorites.controllers.responses.FavoriteResponse;
import com.imaee.propinq.favorites.data.models.Favorite;
import com.imaee.propinq.favorites.mappers.FavoriteMapper;
import com.imaee.propinq.favorites.services.interfaces.IFavoriteService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public FavoriteResponse addFavorite(FavoriteRequest request) {
        final var favorite = favoriteService.addFavorite(request.userID(), request.propertyID(), request.buildingID());
        return favoriteMapper.toResponse(favorite);
    }

    @Override
    public Page<FavoriteEntity> getFavoriteBuildings(Integer pageNumber, Integer pageSize) {
        final var favoritesPage = favoriteService.getFavoriteBuildings(pageNumber, pageSize);
        return favoritesPage.map(favoriteMapper::toBuilding);
    }

    @Override
    public Page<FavoriteEntity> getFavoriteProperties(Integer pageNumber, Integer pageSize) {
        final var favoritesPage = favoriteService.getFavoriteProperties(pageNumber, pageSize);
        return favoritesPage.map(favoriteMapper::toProperty);
    }

    @Override
    public ResponseEntity<Void> removeFavorite(UUID favoriteID) {
        favoriteService.removeFavorite(favoriteID);
        return ResponseEntity.noContent().build();
    }
}