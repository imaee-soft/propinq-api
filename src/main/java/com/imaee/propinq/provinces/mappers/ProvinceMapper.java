package com.imaee.propinq.provinces.mappers;

import com.imaee.propinq.provinces.controllers.responses.ProvinceResponse;
import com.imaee.propinq.provinces.data.models.Province;

public class ProvinceMapper {

    public static ProvinceResponse toProvinceResponse(Province province) {
        return new ProvinceResponse(
                province.getProvinceId(),
                province.getName(),
                province.getLatitude(),
                province.getLongitude()
        );
    }
}
