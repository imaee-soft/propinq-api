package com.imaee.propinq.localities.mappers;

import com.imaee.propinq.localities.controllers.responses.LocalityResponse;
import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.provinces.data.models.Province;

public class LocalityMapper {
    public static Locality toLocality(String name, Province province, Boolean deleted, Double latitude, Double longitude) {
        Locality locality = Locality.builder()
                .name(name)
                .province(province)
                .deleted(deleted)
                .build();
        locality.setLatitude(latitude);
        locality.setLongitude(longitude);
        return  locality;

    }

    public static LocalityResponse toLocalityResponse(Locality locality) {
        return new LocalityResponse(
                locality.getLocalityId(),
                locality.getName(),
                locality.getProvince().getProvinceId(),
                locality.isDeleted(),
                locality.getLatitude(),
                locality.getLongitude()
        );
    }
}

