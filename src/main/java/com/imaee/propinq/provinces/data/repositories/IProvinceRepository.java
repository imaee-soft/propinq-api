package com.imaee.propinq.provinces.data.repositories;

import com.imaee.propinq.provinces.data.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProvinceRepository extends JpaRepository<Province, UUID> {
    List<Province> findAllByOrderByNameAsc();
}