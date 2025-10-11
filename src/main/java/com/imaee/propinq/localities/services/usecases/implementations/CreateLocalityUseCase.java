package com.imaee.propinq.localities.services.usecases.implementations;

import com.imaee.propinq.localities.controllers.requests.LocalityRequest;
import com.imaee.propinq.localities.data.repositories.ILocalityRepository;
import com.imaee.propinq.localities.mappers.LocalityMapper;
import com.imaee.propinq.localities.services.usecases.interfaces.ICreateLocalityUseCase;
import com.imaee.propinq.provinces.data.models.Province;
import com.imaee.propinq.provinces.services.usecases.interfaces.IGetProvinceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.imaee.propinq.localities.utils.Constants.MSG_ALREADY_EXISTS;
import static com.imaee.propinq.localities.utils.Constants.MSG_NOT_EXISTS;

@Component
@AllArgsConstructor
public class CreateLocalityUseCase implements ICreateLocalityUseCase {

    private final ILocalityRepository localityRepository;
    private final IGetProvinceUseCase getProvinceUseCase;
    @Override
    public void createLocality(LocalityRequest localityRequest){
        final Province localityRequestProvince = getLocalityProvince(localityRequest);
        throwExceptionIfLocalityExistsByNameAndProvince(localityRequest.name(), localityRequestProvince);

        final var locality = LocalityMapper.toLocality(
                localityRequest.name(),
                localityRequestProvince,
                false,
                localityRequest.latitude(),
                localityRequest.longitude()
        );

        localityRepository.save(locality);
    }

    private Province getLocalityProvince(LocalityRequest localityRequest) {
        Optional<Province> localityProvince = Optional.ofNullable(getProvinceUseCase.findProvinceByIdOrThrowException(localityRequest.provinceId()));
        if (localityProvince.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_NOT_EXISTS);
        }
        return localityProvince.get();
    }

    private void throwExceptionIfLocalityExistsByNameAndProvince(String name, Province province) {
        if (localityRepository.existsByNameIgnoreCaseAndProvince(name, province)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_ALREADY_EXISTS);
        }
    }

}
