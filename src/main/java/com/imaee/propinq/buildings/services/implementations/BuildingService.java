package com.imaee.propinq.buildings.services.implementations;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.services.interfaces.IBuildingService;
import com.imaee.propinq.buildings.services.usecases.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final IUpdateBuildingUseCase updateBuildingUseCase;
    private final IDeleteBuildingUseCase deleteBuildingUseCase;
    private final IRestoreBuildingUseCase restoreBuildingUseCase;

    @Override
    public void createBuilding(CreateBuildingRequest createBuildingRequest, MultipartFile[] imageFiles) {
        createBuildingUseCase.createBuilding(createBuildingRequest, imageFiles);
    }

    @Override
    public List<BuildingResponse> getBuildings() {
        return getBuildingsUseCase.getBuildings();
    }

    @Override
    public Page<BuildingDetailsResponse> getBuildingsDetails(int page, int size) {
        return getBuildingsUseCase.getBuildingsDetails(page, size);
    }

    @Override
    public BuildingDetailsResponse getBuildingDetails(UUID buildingId) {
        return getBuildingUseCase.getBuilding(buildingId);
    }

    @Override
    public BuildingDetailsResponse updateBuilding(UUID buildingId, UpdateBuildingRequest updateBuildingRequest, MultipartFile[] imageFiles) {
        return updateBuildingUseCase.updateBuilding(buildingId, updateBuildingRequest, imageFiles);
    }

    @Override
    public void deleteBuilding(UUID buildingId) {
        deleteBuildingUseCase.deleteBuilding(buildingId);
    }

    @Override
    public BuildingDetailsResponse restoreBuilding(UUID buildingId) {
        return restoreBuildingUseCase.restoreBuilding(buildingId);
    }

}