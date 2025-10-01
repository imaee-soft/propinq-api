package com.imaee.propinq.properties.services.interfaces;

import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface IPropertyService {

    List<PropertyResponse> getProperties();

    PropertyDetailsResponse getPropertyDetails(UUID propertyId);

    List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId);

    List<PropertyResponse> getPropertiesNear( Double latitude,  Double longitude, Double radiusKm);

    List<PropertyResponse> getPropertiesNearPoi(String poiType,Double radiusKm, Double north,
                                                Double south, Double east, Double west, Integer limit);
}
