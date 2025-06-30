package com.imaee.propinq.neighborhood.data.repositories;

import com.imaee.propinq.neighborhood.data.models.Locality;
import com.imaee.propinq.neighborhood.data.models.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface INeighborhoodRepository extends JpaRepository<Neighborhood, UUID> {
    static final String MSG_NOT_EXISTS = "EL BARRIO CON ESTE ID NO EXISTE";
    static final String MSG_ALREADY_EXISTS = "EL BARRIO CON ESTE NOMBRE Y LOCALIDAD YA EXISTE";

    Optional<Neighborhood> findByNameIgnoreCaseAndLocality(String name, Locality locality);
}
