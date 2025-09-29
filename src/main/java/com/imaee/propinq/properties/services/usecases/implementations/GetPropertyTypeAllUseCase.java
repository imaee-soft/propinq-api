package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.data.repositories.IPropertyTypeRepository;
import com.imaee.propinq.properties.mappers.PropertyTypeMapper;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertyTypeAllUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetPropertyTypeAllUseCase implements IGetPropertyTypeAllUseCase {

    private final IPropertyTypeRepository propertyTypeRepository;

    @Override
    public List<PropertyTypeResponse> getPropertyTypeAll() {
        return propertyTypeRepository.findAll().stream()
                .map(PropertyTypeMapper::toPropertyTypeResponse)
                .toList();
    }
}
