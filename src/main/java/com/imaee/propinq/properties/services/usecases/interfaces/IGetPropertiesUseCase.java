package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.controllers.requests.AttributeFilterRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface  IGetPropertiesUseCase {
    List<PropertyResponse> getProperties(PropertyFilterRequest filter);
    Page<PropertyDetailsResponse> getPropertiesDetails(int page, int size);
    List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId);
    List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId, AttributeFilterRequest attributes);
}
