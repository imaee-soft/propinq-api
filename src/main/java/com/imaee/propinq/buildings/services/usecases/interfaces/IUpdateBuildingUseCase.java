package com.imaee.propinq.buildings.services.usecases.interfaces;

import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

public interface IUpdateBuildingUseCase {
    BuildingDetailsResponse updateBuilding(UUID buildingId, UpdateBuildingRequest updateBuildingRequest, MultipartFile[] imageFiles);
}
