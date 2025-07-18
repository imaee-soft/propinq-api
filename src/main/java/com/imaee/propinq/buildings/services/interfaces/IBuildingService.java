package com.imaee.propinq.buildings.services.interfaces;

import com.imaee.propinq.buildings.controllers.requests.BuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IBuildingService {
    void createBuilding(BuildingRequest buildingRequest, MultipartFile[] images);
    List<BuildingResponse> getBuildings();
    BuildingDetailsResponse getBuilding(UUID buildingId);
}