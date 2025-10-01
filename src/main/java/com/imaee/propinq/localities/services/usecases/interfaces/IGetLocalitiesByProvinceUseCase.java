package com.imaee.propinq.localities.services.usecases.interfaces;

import com.imaee.propinq.localities.controllers.responses.LocalityResponse;
import java.util.List;
import java.util.UUID;

public interface IGetLocalitiesByProvinceUseCase {
    List<LocalityResponse> getLocalitiesByProvince(UUID provinceId);
}
