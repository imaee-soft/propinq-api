package com.imaee.propinq.buildings.services.usecases.managers.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;

import java.util.List;

public interface IBuildingFilterManager {
    List<BuildingResponse> applyFilters(PropertyFilterRequest filter);
}
