package com.imaee.propinq.properties.controllers.implementations;

import com.imaee.propinq.buildings.controllers.interfaces.IBuildingController;
import com.imaee.propinq.properties.controllers.interfaces.IPropertyTypeController;
import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.services.interfaces.IPropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController

public class PropertyTypeController implements IPropertyTypeController {

    private IPropertyTypeService propertyTypeService;

    @Autowired
    public PropertyTypeController(IPropertyTypeService propertyTypeService) {
        this.propertyTypeService = propertyTypeService;
    }

    @Override
    public List<PropertyTypeResponse> getPropertyTypes() {
        return propertyTypeService.getPropertyType();
    }

    @Override
    public PropertyTypeResponse createPropertyType(PropertyTypeRequest propertyTypeRequest) {
        return propertyTypeService.createPropertyType(propertyTypeRequest);
    }

    @Override
    public PropertyTypeResponse updatePropertyType(PropertyTypeRequest propertyTypeRequest, UUID id) {
        return propertyTypeService.updatePropertyType(propertyTypeRequest, id);
    }

    @Override
    public ResponseEntity<Void> deletePropertyType(@PathVariable  UUID id) {
        propertyTypeService.deletePropertyType(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> recoverPropertyType(UUID id) {
        propertyTypeService.recoverPropertyType(id);
        return ResponseEntity.noContent().build();
    }













}
