package com.imaee.propinq.provinces.services.usecases.interfaces;

import com.imaee.propinq.provinces.controllers.responses.ProvinceResponse;

import java.util.List;

public interface IGetProvincesUseCase {

    List<ProvinceResponse> getProvinces();
}
