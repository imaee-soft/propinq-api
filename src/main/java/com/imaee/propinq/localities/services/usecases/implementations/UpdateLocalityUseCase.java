package com.imaee.propinq.localities.services.usecases.implementations;

import com.imaee.propinq.localities.controllers.requests.LocalityRequest;
import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.localities.data.repositories.ILocalityRepository;
import com.imaee.propinq.localities.services.usecases.interfaces.IGetLocalityUseCase;
import com.imaee.propinq.localities.services.usecases.interfaces.IUpdateLocalityUseCase;
import com.imaee.propinq.provinces.services.usecases.interfaces.IGetProvinceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.localities.utils.Constants.MSG_IS_DELETED;


@Component
@AllArgsConstructor
public class UpdateLocalityUseCase implements IUpdateLocalityUseCase {

    private final IGetLocalityUseCase getLocalityUseCase;
    private final IGetProvinceUseCase getProvinceUseCase;
    private final ILocalityRepository localityRepository;

    @Override
    public void updateLocality(LocalityRequest LocalityRequest, UUID localityId) {

        Locality locality = getLocalityUseCase.findLocalityByIdOrThrowException(localityId);
        throwExceptionIfLocalityIsDeleted(locality);
        locality.setName(LocalityRequest.name());
        locality.setProvince(getProvinceUseCase.findProvinceByIdOrThrowException(LocalityRequest.provinceId()));
        locality.setLatitude(LocalityRequest.latitude());
        locality.setLongitude(LocalityRequest.longitude());

        localityRepository.save(locality);
    }

    @Override
    public void throwExceptionIfLocalityIsDeleted(Locality locality) {
        if (locality.isDeleted())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_IS_DELETED);
    }

}
