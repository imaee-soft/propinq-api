package com.imaee.propinq.properties.services.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;

import java.util.List;
import java.util.UUID;

public interface IPropertyService {

    List<PropertyResponse> getProperties(PropertyFilterRequest filter);

    PropertyDetailsResponse getPropertyDetails(UUID propertyId);

    List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId);
}
