package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.data.repositories.IPropertyTypeRepository;
import com.imaee.propinq.properties.mappers.PropertyTypeMapper;
import com.imaee.propinq.properties.services.interfaces.IPropertyTypeService;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertyTypesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetPropertyTypesUseCase implements IGetPropertyTypesUseCase {

    private final IPropertyTypeRepository propertyTypeRepository;

    @Override
    public List<PropertyTypeResponse> getPropertyTypes() {
        return propertyTypeRepository.findByDeletedFalse().stream()
                .map(PropertyTypeMapper::toPropertyTypeResponse)
                .toList();
    }
}
