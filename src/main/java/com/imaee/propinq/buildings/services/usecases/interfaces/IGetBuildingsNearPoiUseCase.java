package com.imaee.propinq.buildings.services.usecases.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;

import java.util.List;

public interface IGetBuildingsNearPoiUseCase {
    List<BuildingResponse> getBuildingsNearPoi(String poiType, Double radiusKm, Double north, Double south, Double east, Double west, Integer limit);
}
