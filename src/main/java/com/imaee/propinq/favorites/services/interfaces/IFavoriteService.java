package com.imaee.propinq.favorites.services.interfaces;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.favorites.data.models.Favorite;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.users.data.models.User;

import java.util.List;
import java.util.UUID;

public interface IFavoriteService {
    //Favorite addPropertyToFavorites(UUID user, UUID property);
    //Favorite addBuildingToFavorites(UUID user, UUID buildingId);
    Favorite addFavorite(UUID user, UUID propertyId, UUID buildingId);
    List<Favorite> getUserFavorites(UUID user);
    List<Favorite> getFavoritesByProperty(UUID user);
    List<Favorite> getFavoritesByBuilding(UUID user);
    void removeFavorite(UUID favoriteId);
}
