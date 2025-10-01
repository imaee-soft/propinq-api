package com.imaee.propinq.localities.data.repositories;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.provinces.data.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ILocalityRepository extends JpaRepository<Locality, UUID> {

    boolean existsByNameIgnoreCaseAndProvince(String name, Province province);
    List<Locality> findAllByProvince_ProvinceId(UUID provinceId);

}
