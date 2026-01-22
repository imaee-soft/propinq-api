package com.imaee.propinq.rents.data.repositories;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.rents.data.models.Contact;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IContactRepository extends JpaRepository<Contact, UUID> {
    boolean existsByIssuerAndProperty(User issuer, Property property);
    Page<Contact> findAllByIssuer_Email(String issuerEmail, Pageable pageable);
    Page<Contact> findAllByProperty_User_Email(String userEmail, Pageable pageable);
    boolean existsByContactIdAndIssuer_Email(UUID contactId, String issuerEmail);
    Optional<Contact> findByPropertyAndIssuer(Property property, User issuer);
}