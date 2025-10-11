package com.imaee.propinq.provinces.services.usecases.interfaces;

import com.imaee.propinq.provinces.controllers.responses.ProvinceResponse;
import com.imaee.propinq.provinces.data.models.Province;

import java.util.UUID;

public interface IGetProvinceUseCase {
    ProvinceResponse getProvince(UUID provinceId);

    Province findProvinceByIdOrThrowException(UUID provinceId);
}
