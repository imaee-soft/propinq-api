package com.imaee.propinq.properties.services.interfaces;

import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IPropertyService {
    List<PropertyResponse> getProperties();
    PropertyDetailsResponse getPropertyDetails(UUID propertyId);
    List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId);
    Page<PropertyDetailsResponse> getPropertiesDetails(int page, int size);
    void createProperty(CreatePropertyRequest request, MultipartFile[] imageFiles);
    List<PropertyResponse> getPropertiesNear(Double latitude, Double longitude, Double radiusKm);
    List<PropertyResponse> getPropertiesNearPoi(String poiType, Double radiusKm, Double north,
                                                Double south, Double east, Double west, Integer limit);
    void restoreProperty(UUID propertyId);
    void deleteProperty(UUID propertyId);
}