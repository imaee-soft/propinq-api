package com.imaee.propinq.contacts.data.repositories;

import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IContactRepository extends JpaRepository<Contact, UUID> {
    boolean existsByIssuerAndProperty(User issuer, Property property);
    Page<Contact> findAllByIssuer_Email(String issuerEmail, Pageable pageable);
    boolean existsByContactIdAndIssuer_Email(UUID contactId, String issuerEmail);
    Optional<Contact> findByPropertyAndIssuer(Property property, User issuer);

    @Query("""
          SELECT c
          FROM contacts c
          WHERE c.property.user = :user
            AND (:status IS NULL OR LOWER(:status) = LOWER(c.state))
            AND (:surname IS NULL OR LOWER(c.issuer.lastName) LIKE LOWER(CONCAT('%', :surname, '%')))
    """)
    Page<Contact> findByOwnerAndOptionalTenantSurname(
            @Param("user") User user,
            @Param("surname") String surname,
            @Param("status") String status,
            Pageable pageable
    );
}