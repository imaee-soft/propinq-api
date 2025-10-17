package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.requests.PoiFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;

import java.util.List;

public interface IGetPropertiesNearPoiUseCase {
    List<PropertyResponse> getPropertiesNearPoi(PoiFilterRequest poiFilter);
}
