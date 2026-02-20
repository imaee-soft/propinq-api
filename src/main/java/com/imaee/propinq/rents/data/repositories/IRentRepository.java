package com.imaee.propinq.rents.data.repositories;

import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IRentRepository extends JpaRepository<Rent, UUID> {
    @Query("""
          SELECT r
          FROM rents r
          WHERE r.contact.property.user = :user
            AND (:surname IS NULL OR LOWER(r.contact.issuer.lastName) LIKE LOWER(CONCAT('%', :surname, '%')))
    """)
    Page<Rent> findByOwnerAndOptionalTenantSurname(
            @Param("user") User user,
            @Param("surname") String surname,
            Pageable pageable
    );

    Page<Rent> findByContact_Issuer(User user, Pageable pageable);
}