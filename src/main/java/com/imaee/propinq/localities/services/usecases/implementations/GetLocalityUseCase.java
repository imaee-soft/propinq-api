package com.imaee.propinq.localities.services.usecases.implementations;

import com.imaee.propinq.localities.controllers.responses.LocalityResponse;
import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.localities.data.repositories.ILocalityRepository;
import com.imaee.propinq.localities.mappers.LocalityMapper;
import com.imaee.propinq.localities.services.usecases.interfaces.IGetLocalityUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.localities.utils.Constants.LOCALITY_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class GetLocalityUseCase implements IGetLocalityUseCase {

    private final ILocalityRepository localityRepository;

    @Override
    public LocalityResponse getLocality(UUID localityID) {
        return LocalityMapper.toLocalityResponse(findLocalityByIdOrThrowException(localityID));

    }

    @Override
    public Locality findLocalityByIdOrThrowException(UUID localityID){
        return  localityRepository.findById(localityID)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, LOCALITY_NOT_FOUND));

    }
}
