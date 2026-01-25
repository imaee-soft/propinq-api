package com.imaee.propinq.favorites.services.interfaces;

import com.imaee.propinq.favorites.data.models.Favorite;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IFavoriteService {
    Favorite addFavorite(UUID user, UUID propertyId, UUID buildingId);
    Page<Favorite> getFavoriteBuildings(Integer pageNumber, Integer pageSize);
    Page<Favorite> getFavoriteProperties(Integer pageNumber, Integer pageSize);
    void removeFavorite(UUID favoriteId);
    boolean existsByUserAndProperty(User user, Property property);
}