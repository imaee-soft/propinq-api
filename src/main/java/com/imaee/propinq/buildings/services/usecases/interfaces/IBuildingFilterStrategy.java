package com.imaee.propinq.buildings.services.usecases.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;

import java.util.List;
import java.util.Set;

public interface IBuildingFilterStrategy {
    boolean canHandle(PropertyFilterRequest filter);
    int getPriority();
    List<BuildingResponse> applyFilter(PropertyFilterRequest filter);
    Set<String> getHandledFields();
}
