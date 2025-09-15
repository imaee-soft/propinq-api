package com.imaee.propinq.properties.data.repositories;

import com.imaee.propinq.properties.data.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface IPropertyRepository extends JpaRepository<Property, UUID>, JpaSpecificationExecutor<Property> {
    List<Property> findAllByDeletedFalseAndBuildingIsNull();
    List<Property> findAllByDeletedFalseAndBuilding_BuildingId(UUID buildingId);
}
