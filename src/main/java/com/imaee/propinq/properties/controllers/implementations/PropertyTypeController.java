package com.imaee.propinq.properties.controllers.implementations;

import com.imaee.propinq.properties.controllers.interfaces.IPropertyTypeController;
import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.services.interfaces.IPropertyTypeService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class PropertyTypeController implements IPropertyTypeController {

    private IPropertyTypeService propertyTypeService;

    @Override
    public List<PropertyTypeResponse> getPropertyTypes() {
        return propertyTypeService.getPropertyTypes();
    }

    @Override
    public List<PropertyTypeResponse> getPropertyTypeAll() {
        return propertyTypeService.getPropertyTypeAll();
    }

    @Override
    public PropertyTypeResponse createPropertyType(PropertyTypeRequest propertyTypeRequest) {
        return propertyTypeService.createPropertyType(propertyTypeRequest);
    }

    @Override
    public PropertyTypeResponse updatePropertyType(UUID propertyTypeId, PropertyTypeRequest propertyTypeRequest) {
        return propertyTypeService.updatePropertyType(propertyTypeId, propertyTypeRequest);
    }

    @Override
    public void deletePropertyType(UUID propertyTypeId) {
        propertyTypeService.deletePropertyType(propertyTypeId);
    }

    @Override
    public void recoverPropertyType(UUID propertyTypeId) {
        propertyTypeService.recoverPropertyType(propertyTypeId);
    }
}