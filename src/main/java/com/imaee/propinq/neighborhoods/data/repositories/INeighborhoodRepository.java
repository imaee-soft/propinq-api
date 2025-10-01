package com.imaee.propinq.neighborhoods.data.repositories;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.neighborhoods.data.models.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface INeighborhoodRepository extends JpaRepository<Neighborhood, UUID> {
    boolean existsByNameIgnoreCaseAndLocality(String name, Locality locality);
}
