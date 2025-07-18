package com.imaee.propinq.buildings.services.implementations;

import com.imaee.propinq.buildings.controllers.requests.BuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.services.interfaces.IBuildingService;
import com.imaee.propinq.buildings.services.usecases.interfaces.ICreateBuildingUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BuildingService implements IBuildingService {

    private final ICreateBuildingUseCase createBuildingUseCase;
    private final IGetBuildingsUseCase getBuildingsUseCase;
    private final IGetBuildingUseCase getBuildingUseCase;

    @Override
    public void createBuilding(BuildingRequest buildingRequest, MultipartFile[] imageFiles) {
        createBuildingUseCase.createBuilding(buildingRequest, imageFiles);
    }

    @Override
    public List<BuildingResponse> getBuildings() {
        return getBuildingsUseCase.getBuildings();
    }

    @Override
    public BuildingDetailsResponse getBuilding(UUID buildingId) {
        return getBuildingUseCase.getBuilding(buildingId);
    }
}