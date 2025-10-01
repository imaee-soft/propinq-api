package com.imaee.propinq.properties.services.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;

import java.util.List;
import java.util.UUID;

public interface IPropertyTypeService {
    List<PropertyTypeResponse> getPropertyTypes();
    List<PropertyTypeResponse> getPropertyTypeAll();
    PropertyTypeResponse createPropertyType(PropertyTypeRequest propertyTypeRequest);
    PropertyTypeResponse updatePropertyType(UUID propertyTypeId, PropertyTypeRequest propertyTypeRequest);
    void deletePropertyType(UUID propertyTypeId);
    void recoverPropertyType(UUID propertyTypeId);
}
