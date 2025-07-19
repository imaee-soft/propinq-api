package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.requests.BuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateBuildingUseCase {

    private final IFindBuildingByIdUseCase findBuildingByIdUseCase;

    @Override
    BuildingDetailsResponse updateBuilding(UUID buildingId, BuildingRequest buildingRequest, MultipartFile[] images){

    }

}
