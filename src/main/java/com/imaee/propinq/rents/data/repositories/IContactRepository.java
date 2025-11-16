package com.imaee.propinq.rents.data.repositories;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.rents.data.models.Contact;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IContactRepository extends JpaRepository<Contact, UUID> {
    boolean existsByIssuerAndProperty(User issuer, Property property);
}