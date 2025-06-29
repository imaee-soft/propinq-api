package com.imaee.propinq.properties.services.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IPropertyTypeService {
    List<PropertyTypeResponse> getPropertyType();
    PropertyTypeResponse createPropertyType(PropertyTypeRequest propertyTypeRequest);
    PropertyTypeResponse updatePropertyType(PropertyTypeRequest propertyTypeRequest, UUID id);
    void deletePropertyType(UUID id);
    void recoverPropertyType(UUID id);
}
