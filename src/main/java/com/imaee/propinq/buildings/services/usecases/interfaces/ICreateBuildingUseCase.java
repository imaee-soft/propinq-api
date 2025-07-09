package com.imaee.propinq.buildings.services.usecases.interfaces;

import com.imaee.propinq.buildings.controllers.requests.BuildingRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ICreateBuildingUseCase {
    void createBuilding(BuildingRequest  buildingRequest, MultipartFile[] imageFiles);
}