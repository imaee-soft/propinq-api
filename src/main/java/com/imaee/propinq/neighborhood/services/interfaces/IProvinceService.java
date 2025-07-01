package com.imaee.propinq.neighborhood.services.interfaces;

import com.imaee.propinq.neighborhood.controllers.responses.ProvinceResponse;

import java.util.List;

public interface IProvinceService {
    List<ProvinceResponse> getProvinces();
}
