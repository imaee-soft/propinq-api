package com.imaee.propinq.neighborhood.mappers;

import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import com.imaee.propinq.neighborhood.data.models.Locality;

public class LocalityMapper {

    public static LocalityResponse toResponse(Locality locality) {
        return new LocalityResponse(
                locality.getId(),
                locality.getName()
        );
    }

    public static Locality toEntity(String name) {
        return Locality.builder()
                .name(name)
                .build();
    }

}
