package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.responses.PropertyResponse;

import java.util.List;

public interface IGetPropertiesNearPoiUseCase {
    List<PropertyResponse> getPropertiesNearPoi(String poiType, Double radiusKm, Double north,
                                                Double south, Double east, Double west, Integer limit);
}
