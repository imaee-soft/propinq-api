package com.imaee.propinq.localities.services.usecases.implementations;

import com.imaee.propinq.localities.controllers.responses.LocalityResponse;
import com.imaee.propinq.localities.data.repositories.ILocalityRepository;
import com.imaee.propinq.localities.mappers.LocalityMapper;
import com.imaee.propinq.localities.services.usecases.interfaces.IGetLocalitiesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetLocalitiesUseCase implements IGetLocalitiesUseCase {

    private ILocalityRepository localityRepository;

    @Override
    public List<LocalityResponse> getLocalities() {
       return localityRepository
                .findAll()
                .stream()
                .map(LocalityMapper::toLocalityResponse)
                .collect(Collectors.toList());
    }
}
