package com.imaee.propinq.properties.services.implementations;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.services.interfaces.IPropertyTypeService;
import com.imaee.propinq.properties.services.usecases.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PropertyTypeService implements IPropertyTypeService {

    private final IGetPropertyTypesUseCase getPropertyTypesUseCase;
    private final IGetPropertyTypeAllUseCase getPropertyTypeAllUseCase;
    private final ICreatePropertyTypeUseCase createPropertyTypeUseCase;
    private final IUpdatePropertyTypeUseCase updatePropertyTypeUseCase;
    private final IDeletePropertyTypeUseCase deletePropertyTypeUseCase;
    private final IRecoverPropertyTypeUseCase recoverPropertyTypeUseCase;

    @Override
    public List<PropertyTypeResponse> getPropertyTypes() {
        return getPropertyTypesUseCase.getPropertyTypes();
    }

    @Override
    public List<PropertyTypeResponse> getPropertyTypeAll() {
        return getPropertyTypeAllUseCase.getPropertyTypeAll();
    }

    @Override
    public PropertyTypeResponse createPropertyType(PropertyTypeRequest propertyTypeRequest) {
        return createPropertyTypeUseCase.createPropertyType(propertyTypeRequest);
    }

    @Override
    public PropertyTypeResponse updatePropertyType(UUID propertyTypeId, PropertyTypeRequest propertyTypeRequest) {
        return updatePropertyTypeUseCase.updatePropertyType(propertyTypeId, propertyTypeRequest);
    }

    @Override
    public void deletePropertyType(UUID propertyTypeId) {
        deletePropertyTypeUseCase.deletePropertyType(propertyTypeId);
    }

    @Override
    public void recoverPropertyType(UUID propertyTypeId) {
        recoverPropertyTypeUseCase.recoverPropertyType(propertyTypeId);
    }
}
