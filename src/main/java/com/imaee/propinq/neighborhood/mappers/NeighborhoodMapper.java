package com.imaee.propinq.neighborhood.mappers;

import com.imaee.propinq.neighborhood.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhood.data.models.Locality;
import com.imaee.propinq.neighborhood.data.models.Neighborhood;

public class NeighborhoodMapper {
    public static Neighborhood toNeighborhood(String name,
                                              Locality locality) {
        return Neighborhood.builder()
                .name(name)
                .locality(locality)
                .build();
    }

    public static NeighborhoodResponse toNeighborhoodResponse(Neighborhood neighborhood) {
        return new NeighborhoodResponse(
                neighborhood.getId(),
                neighborhood.getName(),
                neighborhood.getLocality().getId()
        );
    }
}
