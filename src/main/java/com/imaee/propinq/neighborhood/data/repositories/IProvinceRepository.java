package com.imaee.propinq.neighborhood.data.repositories;

import com.imaee.propinq.neighborhood.data.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProvinceRepository extends JpaRepository<Province, Integer> {
}
