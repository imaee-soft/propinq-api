package com.imaee.propinq.buildings.data.repositories;

import com.imaee.propinq.buildings.data.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBuildingRepository extends JpaRepository<Building, UUID> {
    boolean existsByName(String name);
}
