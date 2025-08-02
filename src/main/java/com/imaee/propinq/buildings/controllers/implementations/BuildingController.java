package com.imaee.propinq.buildings.controllers.implementations;

import com.imaee.propinq.buildings.controllers.interfaces.IBuildingController;
import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.services.interfaces.IBuildingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class BuildingController implements IBuildingController {

    private final IBuildingService buildingService;

    @Override
    public void createBuilding(CreateBuildingRequest createBuildingRequest, MultipartFile[] imageFiles) {
        buildingService.createBuilding(createBuildingRequest, imageFiles);
    }

    @Override
    public List<BuildingResponse> getBuildings() {
        return buildingService.getBuildings();
    }

    @Override
    public Page<BuildingDetailsResponse> getBuildingsDetails(int page, int size) {
        return buildingService.getBuildingsDetails(page, size);
    }

    @Override
    public BuildingDetailsResponse getBuildingDetails(UUID buildingId) {
        return buildingService.getBuildingDetails(buildingId);
    }

    @Override
    public BuildingDetailsResponse updateBuilding (UUID buildingId, UpdateBuildingRequest updateBuildingRequest, MultipartFile[] imageFiles) {
        return buildingService.updateBuilding(buildingId, updateBuildingRequest, imageFiles);
    }

    @Override
    public void deleteBuilding(UUID buildingId) {
        buildingService.deleteBuilding(buildingId);
    }

    @Override
    public void restoreBuilding(UUID buildingId) {
        return buildingService.restoreBuilding(buildingId);
    }
}
