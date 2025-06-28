package com.imaee.propinq.neighborhood.mappers;

import com.imaee.propinq.neighborhood.controllers.responses.ProvinceResponse;
import com.imaee.propinq.neighborhood.data.models.Province;

public class ProvinceMapper {
    public static ProvinceResponse toProvinceResponse(Province province) {
        return new ProvinceResponse(
                province.getId(),
                province.getName()
        );
    }
}
