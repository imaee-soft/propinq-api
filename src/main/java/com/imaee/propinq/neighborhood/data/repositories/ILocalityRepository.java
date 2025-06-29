package com.imaee.propinq.neighborhood.data.repositories;

import com.imaee.propinq.neighborhood.data.models.Locality;
import com.imaee.propinq.neighborhood.data.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ILocalityRepository extends JpaRepository<Locality, UUID> {
    static final String MSG_NOT_EXISTS = "LOCALITY WITH THIS ID NOT EXISTS";
    static final String MSG_ALREADY_EXISTS = "LOCALITY WITH THIS NAME AND PROVINCE ALREADY EXISTS";

    Optional<Locality> findByNameIgnoreCaseAndProvince(String name, Province province);
}
