package com.imaee.propinq.localities.services.usecases.implementations;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.localities.data.repositories.ILocalityRepository;
import com.imaee.propinq.localities.services.usecases.interfaces.IDeleteLocalityUseCase;
import com.imaee.propinq.localities.services.usecases.interfaces.IGetLocalityUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteLocalityUseCase implements IDeleteLocalityUseCase {

    private final IGetLocalityUseCase getLocalityUseCase;
    private final ILocalityRepository localityRepository;
    private final UpdateLocalityUseCase updateLocalityUseCase;

    @Override
    public void deleteLocality(java.util.UUID localityId) {
        Locality locality = getLocalityUseCase.findLocalityByIdOrThrowException(localityId);
        updateLocalityUseCase.throwExceptionIfLocalityIsDeleted(locality);
        locality.setDeleted(true);

        localityRepository.save(locality);
    }
}
