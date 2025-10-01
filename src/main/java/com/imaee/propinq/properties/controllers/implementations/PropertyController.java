package com.imaee.propinq.properties.controllers.implementations;

import com.imaee.propinq.properties.controllers.interfaces.IPropertyController;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.interfaces.IPropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class PropertyController implements IPropertyController {
    private final IPropertyService propertyService;

    @Override
    public List<PropertyResponse> getProperties() {
        return propertyService.getProperties();
    }

    @Override
    public PropertyDetailsResponse getPropertyDetails(UUID propertyId) {
        return propertyService.getPropertyDetails(propertyId);
    }

    @Override
    public List<PropertyResponse> getPropertiesNear( Double latitude,  Double longitude, Double radiusKm) {
           return propertyService.getPropertiesNear(latitude, longitude, radiusKm);
    }

    @Override
    public List<PropertyResponse> getPropertiesNearPoi(String poiType,Double radiusKm, Double north,
                                                       Double south, Double east, Double west, Integer limit) {
        return propertyService.getPropertiesNearPoi(poiType, radiusKm, north, south, east, west, limit);
    }
}
