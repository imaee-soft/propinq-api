package com.imaee.propinq.localities.services.usecases.interfaces;

import com.imaee.propinq.localities.controllers.responses.LocalityResponse;

import java.util.List;

public interface IGetLocalitiesUseCase {
    List<LocalityResponse> getLocalities();
}
