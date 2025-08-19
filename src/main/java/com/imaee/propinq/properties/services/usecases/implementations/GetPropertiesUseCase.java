package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class GetPropertiesUseCase implements IGetPropertiesUseCase {
    private final IPropertyRepository propertyRepository;

    @Override
    public List<PropertyResponse> getProperties() {
        return propertyRepository.findAllByDeletedFalse()
                .stream()
                .map(PropertyMapper::toPropertyResponse)
                .toList();
    }
}
