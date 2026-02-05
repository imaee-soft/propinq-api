package com.imaee.propinq.rents.data.repositories;

import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRentRepository extends JpaRepository<Rent, UUID> {
    Page<Rent> findByContact_Property_User(User user, Pageable pageable);
}