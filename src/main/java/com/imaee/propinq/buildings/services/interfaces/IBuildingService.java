package com.imaee.propinq.buildings.services.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;

import java.util.List;
import java.util.UUID;

public interface IBuildingService {

    List<BuildingResponse> getBuildings();

    BuildingResponse getBuilding(UUID buildingId);
}
