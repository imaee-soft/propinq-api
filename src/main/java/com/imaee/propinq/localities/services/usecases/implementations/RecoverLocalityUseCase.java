package com.imaee.propinq.localities.services.usecases.implementations;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.localities.data.repositories.ILocalityRepository;
import com.imaee.propinq.localities.services.usecases.interfaces.IGetLocalityUseCase;
import com.imaee.propinq.localities.services.usecases.interfaces.IRecoverLocalityUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RecoverLocalityUseCase implements IRecoverLocalityUseCase {

    private final IGetLocalityUseCase getLocalityUseCase;
    private final ILocalityRepository localityRepository;

    @Override
    public void recoverLocality(UUID localityId) {
        Locality locality = getLocalityUseCase.findLocalityByIdOrThrowException(localityId);

        locality.setDeleted(false);

        localityRepository.save(locality);
    }

}
