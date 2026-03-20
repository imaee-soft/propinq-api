package com.imaee.propinq.buildings.services.usecases.interfaces;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ICreateBuildingUseCase {
    void createBuilding(CreateBuildingRequest createBuildingRequest, MultipartFile[] imageFiles);
}