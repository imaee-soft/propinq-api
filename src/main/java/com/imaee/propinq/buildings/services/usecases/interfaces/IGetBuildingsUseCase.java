package com.imaee.propinq.buildings.services.usecases.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGetBuildingsUseCase {
    List<BuildingResponse> getBuildings();
    Page<BuildingDetailsResponse> getBuildingsDetails(int page, int size);
}
