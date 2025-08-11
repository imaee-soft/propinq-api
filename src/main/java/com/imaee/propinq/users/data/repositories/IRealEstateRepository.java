package com.imaee.propinq.users.data.repositories;

import com.imaee.propinq.users.data.models.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRealEstateRepository extends JpaRepository<RealEstate, UUID> {
    boolean existsByCompanyNameAndCuit(String companyName, String cuit);
}