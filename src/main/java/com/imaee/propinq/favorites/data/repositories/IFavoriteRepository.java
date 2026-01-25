package com.imaee.propinq.favorites.data.repositories;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.favorites.data.models.Favorite;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IFavoriteRepository extends JpaRepository<Favorite, UUID>{
    Page<Favorite> findByUserIDAndPropertyIDIsNotNull(User user, Pageable pageable);
    Page<Favorite> findByUserIDAndBuildingIDIsNotNull(User user, Pageable pageable);
    boolean existsByUserIDAndPropertyID(User user, Property property);
    boolean existsByUserIDAndBuildingID(User user, Building building);
    Favorite findByUserIDAndPropertyID(User user, Property property);
    Favorite findByUserIDAndBuildingID(User user, Building building);
}