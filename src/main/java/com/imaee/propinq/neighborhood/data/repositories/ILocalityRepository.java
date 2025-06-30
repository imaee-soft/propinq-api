package com.imaee.propinq.neighborhood.data.repositories;

import com.imaee.propinq.neighborhood.data.models.Locality;
import com.imaee.propinq.neighborhood.data.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ILocalityRepository extends JpaRepository<Locality, UUID> {
    static final String MSG_NOT_EXISTS = "LA LOCALIDAD CON ESTE ID NO EXISTE";
    static final String MSG_ALREADY_EXISTS = "LA LOCALIDAD CON ESTE NOMBRE Y PROVINCIA YA EXISTE";

    boolean existsByNameIgnoreCaseAndProvince(String name, Province province);
}
