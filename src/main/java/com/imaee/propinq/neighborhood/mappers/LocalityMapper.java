package com.imaee.propinq.neighborhood.mappers;

import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import com.imaee.propinq.neighborhood.data.models.Locality;

public class LocalityMapper {

    public static Locality toLocality(String name) {
        return Locality.builder()
                .name(name)
                .build();
    }

    public static LocalityResponse toLocalityResponse(Locality locality) {
        return new LocalityResponse(
                locality.getId(),
                locality.getName()
        );
    }

}
