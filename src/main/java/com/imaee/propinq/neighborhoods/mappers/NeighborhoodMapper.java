package com.imaee.propinq.neighborhoods.mappers;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.data.models.Neighborhood;

public class NeighborhoodMapper {
    public static Neighborhood toNeighborhood(String name, Locality locality, Boolean deleted,
                                              Double latitude, Double longitude) {
        Neighborhood neighborhood = Neighborhood.builder()
                .name(name)
                .locality(locality)
                .deleted(deleted)
                .build();
        neighborhood.setLatitude(latitude);
        neighborhood.setLongitude(longitude);
        return neighborhood;


    }

    public static NeighborhoodResponse toNeighborhoodResponse(Neighborhood neighborhood) {
        return new NeighborhoodResponse(
                neighborhood.getNeighborhoodId(),
                neighborhood.getName(),
                neighborhood.getLocality().getLocalityId(),
                neighborhood.isDeleted(),
                neighborhood.getLatitude(),
                neighborhood.getLongitude()
        );
    }
}
