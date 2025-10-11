package com.imaee.propinq.properties.data.repositories;

import com.imaee.propinq.properties.data.models.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPropertyTypeRepository extends JpaRepository<PropertyType, UUID> {
    boolean existsByNameAndDeletedFalse(String name);
    List<PropertyType> findByDeletedFalse();
    Optional<PropertyType> findByName(String name);
    Optional<PropertyType> findByDescription(String description);
    Optional<PropertyType> findById(UUID propertyTypeId);
}
