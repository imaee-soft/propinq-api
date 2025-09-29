package com.imaee.propinq.properties.data.repositories;

import com.imaee.propinq.properties.data.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, UUID> {
    List<Property> findAllByDeletedFalseAndBuildingIsNull();
    List<Property> findAllByDeletedFalseAndBuilding_BuildingId(UUID buildingId);
}
