package com.imaee.propinq.provinces.services.usecases.implementations;

import com.imaee.propinq.provinces.controllers.responses.ProvinceResponse;
import com.imaee.propinq.provinces.data.repositories.IProvinceRepository;
import com.imaee.propinq.provinces.mappers.ProvinceMapper;
import com.imaee.propinq.provinces.services.usecases.interfaces.IGetProvincesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetProvincesUseCase implements IGetProvincesUseCase {
    private final IProvinceRepository provinceRepository;
    @Override
    public List<ProvinceResponse> getProvinces() {
        return provinceRepository
                .findAllByOrderByNameAsc()
                .stream()
                .map(ProvinceMapper::toProvinceResponse)
                .collect(Collectors.toList());
    }
}
