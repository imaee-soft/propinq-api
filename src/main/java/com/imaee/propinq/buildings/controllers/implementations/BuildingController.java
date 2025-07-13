package com.imaee.propinq.buildings.controllers.implementations;

import com.imaee.propinq.buildings.controllers.interfaces.IBuildingController;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.services.interfaces.IBuildingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class BuildingController implements IBuildingController {

    private final IBuildingService buildingService;

    @Override
    public List<BuildingResponse> getBuildings() {
        return buildingService.getBuildings();
    }

    @Override
    public BuildingDetailsResponse getBuilding(UUID buildingId) {
        return buildingService.getBuilding(buildingId);
    }
}
