package com.imaee.propinq.buildings.services.usecases.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;

import java.util.UUID;

public interface IGetBuildingUseCase {
    BuildingDetailsResponse getBuilding(UUID buildingId);
}
