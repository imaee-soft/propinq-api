package com.imaee.propinq.properties.services.interfaces;

import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IPropertyService {
    List<PropertyResponse> getProperties();
    PropertyDetailsResponse getPropertyDetails(UUID propertyId);
    List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId);
    void createProperty(CreatePropertyRequest request, MultipartFile[] imageFiles);
}