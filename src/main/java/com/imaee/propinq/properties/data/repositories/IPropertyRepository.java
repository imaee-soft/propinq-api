package com.imaee.propinq.properties.data.repositories;

import com.imaee.propinq.properties.data.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IPropertyRepository extends JpaRepository<Property, UUID> {
    List<Property> findAllByDeletedFalse();
}
