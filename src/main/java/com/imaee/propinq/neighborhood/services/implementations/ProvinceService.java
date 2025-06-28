package com.imaee.propinq.neighborhood.services.implementations;

import com.imaee.propinq.neighborhood.controllers.responses.ProvinceResponse;
import com.imaee.propinq.neighborhood.data.repositories.IProvinceRepository;
import com.imaee.propinq.neighborhood.mappers.ProvinceMapper;
import com.imaee.propinq.neighborhood.services.interfaces.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinceService implements IProvinceService {
    @Autowired
    private IProvinceRepository provinceRepository;

    @Override
    public ResponseEntity<List<ProvinceResponse>> getProvinces() {
        return ResponseEntity.ok(
                provinceRepository
                        .findAll()
                        .stream()
                        .map(ProvinceMapper::toProvinceResponse)
                        .collect(Collectors.toList())
        );
    }
}
