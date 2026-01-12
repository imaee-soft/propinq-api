package com.imaee.propinq.properties.services.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.requests.AttributeFilterRequest;
import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.controllers.requests.UpdatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IPropertyService {

    List<PropertyResponse> getProperties(PropertyFilterRequest filter);
    PropertyDetailsResponse getPropertyDetails(UUID propertyId);
    Page<PropertyDetailsResponse> getPropertiesDetails(int page, int size);
    List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId, AttributeFilterRequest attributes);
    void createProperty(CreatePropertyRequest request, MultipartFile[] imageFiles);
    PropertyDetailsResponse updateProperty(UUID propertyId, UpdatePropertyRequest updatePropertyRequest, MultipartFile[] imageFiles);
    void restoreProperty(UUID propertyId);
    void deleteProperty(UUID propertyId);
}

