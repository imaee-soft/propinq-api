package com.imaee.propinq.properties.data.repositories;

import com.imaee.propinq.properties.data.models.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, UUID>, JpaSpecificationExecutor<Property> {
    List<Property> findAllByDeletedFalseAndBuildingIsNull();
    List<Property> findAllByDeletedFalseAndBuilding_BuildingId(UUID buildingId);
    boolean existsByApartmentNumberAndBuildingBuildingId(String apartmentName, UUID buildingId);
    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);
    Page<Property> findAllByUser_UserId(UUID userId, Pageable pageable);
    @Query("""
           SELECT pr FROM properties pr
           WHERE pr.latitude BETWEEN :south AND :north
             AND (
                   (:crossesDateline = FALSE AND pr.longitude BETWEEN :west AND :east)
                   OR
                   (:crossesDateline = TRUE AND (pr.longitude >= :west OR pr.longitude <= :east))
                 )
             AND ( 
                    6371 * 2 * ASIN(
                        SQRT(
                            POWER(SIN(RADIANS(:centerLat - pr.latitude) / 2), 2) +
                            COS(RADIANS(pr.latitude)) * COS(RADIANS(:centerLat)) *
                            POWER(SIN(RADIANS(:centerLng - pr.longitude) / 2), 2)
                        )
                    ) <= :radiusKm
                 )
             AND pr.deleted = FALSE
             AND pr.building IS NULL 
           """)
    List<Property> findWithinRadiusAndBounds(
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
