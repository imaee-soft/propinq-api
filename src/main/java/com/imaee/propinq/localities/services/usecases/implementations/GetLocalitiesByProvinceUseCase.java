package com.imaee.propinq.localities.services.usecases.implementations;

import com.imaee.propinq.localities.controllers.responses.LocalityResponse;
import com.imaee.propinq.localities.data.repositories.ILocalityRepository;
import com.imaee.propinq.localities.mappers.LocalityMapper;
import com.imaee.propinq.localities.services.usecases.interfaces.IGetLocalitiesByProvinceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetLocalitiesByProvinceUseCase implements IGetLocalitiesByProvinceUseCase {

    private final ILocalityRepository localityRepository;

    @Override
    public List<LocalityResponse> getLocalitiesByProvince(UUID provinceId) {
        return localityRepository.findAllByProvince_ProvinceIdOrderByNameAsc(provinceId)
                .stream()
                .map(LocalityMapper::toLocalityResponse)
                .collect(Collectors.toList());
    }
}
