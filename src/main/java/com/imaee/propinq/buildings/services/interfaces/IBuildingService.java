package com.imaee.propinq.buildings.services.interfaces;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.requests.AttributeFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IBuildingService {
    void createBuilding(CreateBuildingRequest createBuildingRequest, MultipartFile[] images);
    List<BuildingResponse> getBuildings(PropertyFilterRequest filter, boolean includeProperties, Integer propertiesLimit);
    Page<BuildingDetailsResponse> getBuildingsDetails(int page, int size);
    BuildingDetailsResponse getBuildingDetails(UUID buildingId);
    BuildingDetailsResponse updateBuilding(UUID buildingId, UpdateBuildingRequest updateBuildingRequest, MultipartFile[] imageFiles);
    void deleteBuilding(UUID buildingId);
    void restoreBuilding(UUID buildingId);

    List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId, AttributeFilterRequest attributes);

    List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId);
    boolean hasApartment(UUID buildingId, String name);
}