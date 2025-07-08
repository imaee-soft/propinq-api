package com.imaee.propinq.buildings.data.repositories;

import com.imaee.propinq.buildings.data.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IBuildingRepository extends JpaRepository<Building, UUID> {
    @Query("SELECT b FROM buildings b LEFT JOIN FETCH b.images WHERE b.buildingId = :id")
    Optional<Building> findByIdWithImages(@Param("id") UUID id);
}
