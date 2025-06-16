package com.imaee.propinq.buildings.controllers.implementations;

import com.imaee.propinq.buildings.controllers.interfaces.IBuildingController;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.services.interfaces.IBuildingService;

import java.util.List;
import java.util.UUID;

public class BuildingController implements IBuildingController {

    private final IBuildingService buildingService;
    public BuildingController(IBuildingService buildingService) {
        this.buildingService = buildingService;
    }
    @Override
    public List<BuildingResponse> getBuildings() {

        return buildingService.getBuildings();
    }

    @Override
    public BuildingResponse getBuilding(UUID buildingId) {

        return buildingService.getBuilding(buildingId);
    }
}
