package com.imaee.propinq.provinces.services.interfaces;

import com.imaee.propinq.provinces.controllers.responses.ProvinceResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface IProvinceService {

    List<ProvinceResponse> getProvinces();

    ProvinceResponse getProvince(UUID provinceId);
}
