package com.imaee.propinq.properties.data.repositories;

import com.imaee.propinq.properties.data.models.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPropertyTypeRepository extends JpaRepository<PropertyType, UUID> {
    List<PropertyType> findByState(boolean state);
    Optional<PropertyType> findByName(String name);
    Optional<PropertyType> findByDescription(String description);
    Optional<PropertyType> findById(UUID id);
}
