package com.imaee.propinq.favorites.services.implementations;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.favorites.data.models.Favorite;
import com.imaee.propinq.favorites.data.repositories.IFavoriteRepository;
import com.imaee.propinq.favorites.services.interfaces.IFavoriteService;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.data.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FavoriteService implements IFavoriteService {

    private final IFavoriteRepository favoriteRepository;
    private final IUserRepository userRepository;
    private final IPropertyRepository propertyRepository;
    private final IBuildingRepository buildingRepository;

    @Override
    public Favorite addFavorite(UUID userId, UUID propertyId, UUID buildingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        boolean hasProperty = propertyId != null;
        boolean hasBuilding = buildingId != null;
        if (hasProperty == hasBuilding) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide exactly one of propertyID or buildingID");
        }
        if (hasProperty) {
            Property property = propertyRepository.findById(propertyId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));
            if (existsByUserAndProperty(user, property)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Property already favorited");
            }
            Favorite fav = new Favorite();
            fav.setUserID(user);
            fav.setPropertyID(property);
            fav.setBuildingID(null);
            return favoriteRepository.save(fav);
        } else {
            Building building = buildingRepository.findById(buildingId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Building not found"));

            if (favoriteRepository.existsByUserIDAndBuildingID(user, building)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Building already favorited");
            }
            Favorite fav = new Favorite();
            fav.setUserID(user);
            fav.setBuildingID(building);
            fav.setPropertyID(null);
            return favoriteRepository.save(fav);
        }
    }


    @Override
    public List<Favorite> getUserFavorites(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        return favoriteRepository.findByUserID(user);
    }

    @Override
    public List<Favorite> getFavoritesByProperty(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        return favoriteRepository.findByUserIDAndPropertyIDIsNotNull(user);
    }

    @Override
    public List<Favorite> getFavoritesByBuilding(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        return favoriteRepository.findByUserIDAndBuildingIDIsNotNull(user);
    }

    @Override
    public void removeFavorite(UUID favoriteId) {
        if (!favoriteRepository.existsById(favoriteId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Favorite not found");
        }
        favoriteRepository.deleteById(favoriteId);
    }

    @Override
    public boolean existsByUserAndProperty(User user, Property property) {
        return favoriteRepository.existsByUserIDAndPropertyID(user, property);
    }
}