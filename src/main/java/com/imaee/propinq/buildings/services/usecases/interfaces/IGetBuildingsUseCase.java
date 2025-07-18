package com.imaee.propinq.buildings.services.usecases.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;

import java.util.List;

public interface IGetBuildingsUseCase {
    List<BuildingResponse> getBuildings();
}
