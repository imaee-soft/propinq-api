package com.imaee.propinq.localities.services.implementations;


import com.imaee.propinq.localities.controllers.requests.LocalityRequest;
import com.imaee.propinq.localities.controllers.responses.LocalityResponse;
import com.imaee.propinq.localities.services.interfaces.ILocalityService;
import com.imaee.propinq.localities.services.usecases.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LocalityService implements ILocalityService {

    private final IGetLocalitiesUseCase getLocalitiesUseCase;
    private final IGetLocalityUseCase getLocalityUseCase;
    private final ICreateLocalityUseCase createLocalityUseCase;
    private final IUpdateLocalityUseCase updateLocalityUseCase;
    private final IDeleteLocalityUseCase deleteLocalityUseCase;
    private final IRecoverLocalityUseCase recoverLocalityUseCase;
    private final IGetLocalitiesByProvinceUseCase getLocalitiesByProvinceUseCase;

    @Override
    public void createLocality(LocalityRequest localityRequest){
        createLocalityUseCase.createLocality(localityRequest);
    }

    @Override
    public List<LocalityResponse> getLocalities(){
        return getLocalitiesUseCase.getLocalities();
    }

    @Override
    public LocalityResponse getLocality(UUID localityId){
        return getLocalityUseCase.getLocality(localityId);
    }

    @Override
    public void updateLocality(LocalityRequest LocalityRequest,UUID localityId){
        updateLocalityUseCase.updateLocality(LocalityRequest,localityId);
    }

    @Override
    public void deleteLocality(UUID localityId){
        deleteLocalityUseCase.deleteLocality(localityId);
    }

    @Override
    public void recoverLocality(UUID localityId){
        recoverLocalityUseCase.recoverLocality(localityId);
    }

    @Override
    public List<LocalityResponse> getLocalitiesByProvince(@PathVariable UUID provinceId) {
        return getLocalitiesByProvinceUseCase.getLocalitiesByProvince(provinceId);
    }
}
