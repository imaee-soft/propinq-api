package com.imaee.propinq.buildings.data.repositories;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBuildingRepository extends JpaRepository<Building, UUID> {
    @Query("SELECT b FROM buildings b LEFT JOIN FETCH b.properties WHERE b.buildingId = :id")
    Optional<Building> findByIdWithProperties(@Param("id") UUID id);

    @Query("SELECT b FROM buildings b LEFT JOIN FETCH b.images WHERE b.buildingId = :id")
    Optional<Building> findByIdWithImages(@Param("id") UUID id);
  
    boolean existsByName(String name);

    List<Building> findAllByDeletedFalse();

    Page<Building> findAllByUser(User user, Pageable pageable);

    @Query("""
           SELECT b FROM buildings b
           WHERE b.latitude BETWEEN :south AND :north
             AND (
                   (:crossesDateline = FALSE AND b.longitude BETWEEN :west AND :east)
                   OR
                   (:crossesDateline = TRUE AND (b.longitude >= :west OR b.longitude <= :east))
                 )
             AND (
                    6371 * 2 * ASIN(
                        SQRT(
                            POWER(SIN(RADIANS(:centerLat - b.latitude) / 2), 2) +
                            COS(RADIANS(b.latitude)) * COS(RADIANS(:centerLat)) *
                            POWER(SIN(RADIANS(:centerLng - b.longitude) / 2), 2)
                        )
                    ) <= :radiusKm
                 )
             AND b.deleted = FALSE
           """)
    List<Building> findWithinRadiusAndBounds(
            @Param("centerLat") double centerLat,
            @Param("centerLng") double centerLng,
            @Param("radiusKm") double radiusKm,
            @Param("south") double south,
            @Param("north") double north,
            @Param("west") double west,
            @Param("east") double east,
            @Param("crossesDateline") boolean crossesDateline,
            Pageable pageable
    );
}
