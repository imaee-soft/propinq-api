package com.imaee.propinq.buildings.services.implementations;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.services.interfaces.IBuildingService;
import com.imaee.propinq.buildings.services.usecases.interfaces.ICreateBuildingUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IDeleteBuildingUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IExistsApartmentNumberUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsNearPoiUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsNearUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IRestoreBuildingUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IUpdateBuildingUseCase;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.services.interfaces.IPropertyService;
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
    private final IPropertyService propertyService;
    private final IGetBuildingsNearUseCase getBuildingsNearUseCase;
    private final IGetBuildingsNearPoiUseCase getBuildingsNearPoiUseCase;
    private final IExistsApartmentNumberUseCase existsApartmentNumberUseCase;

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
    public void restoreBuilding(UUID buildingId) {
        restoreBuildingUseCase.restoreBuilding(buildingId);
    }

    @Override
    public List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId) {
        return propertyService.getBuildingProperties(buildingId);
    }

    @Override
    public boolean hasApartment(UUID buildingId, String name) {
        return existsApartmentNumberUseCase.existsApartmentNumber(buildingId, name);
    }

    @Override
    public List<BuildingResponse> getBuildingsNear(Double latitude, Double longitude, Double radiusKm) {
        return getBuildingsNearUseCase.getBuildingsNear(latitude, longitude, radiusKm);
    }

    @Override
    public List<BuildingResponse> getBuildingsNearPoi(String poiType, Double radiusKm, Double north, Double south,
                                                      Double east, Double west, Integer limit) {
        return getBuildingsNearPoiUseCase.getBuildingsNearPoi(poiType, radiusKm, north, south, east, west, limit);
    }
}