package com.imaee.propinq.localities.services.usecases.interfaces;

import com.imaee.propinq.localities.controllers.responses.LocalityResponse;
import com.imaee.propinq.localities.data.models.Locality;

import java.util.UUID;

public interface IGetLocalityUseCase {
    LocalityResponse getLocality(UUID localityId);
    Locality findLocalityByIdOrThrowException(UUID localityID);
}
