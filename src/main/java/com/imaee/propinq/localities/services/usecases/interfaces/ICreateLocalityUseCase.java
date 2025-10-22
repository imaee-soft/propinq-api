package com.imaee.propinq.localities.services.usecases.interfaces;

import com.imaee.propinq.localities.controllers.requests.LocalityRequest;

public interface ICreateLocalityUseCase {
    void createLocality(LocalityRequest localityRequest);
}
