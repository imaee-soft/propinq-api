package com.imaee.propinq.properties.services.usecases.implementations;

import java.util.List;

import org.springframework.stereotype.Component;

import com.imaee.propinq.properties.controllers.requests.AttributeFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.data.repositories.specifications.PropertySpecifications;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesByAttributesUseCase;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GetPropertiesByAttributesUseCase implements IGetPropertiesByAttributesUseCase {
    
    private final IPropertyRepository propertyRepository;
    
    @Override
    public List<PropertyResponse> getPropertiesByAttributes(AttributeFilterRequest attributeFilter) {
        return propertyRepository.findAll(
            PropertySpecifications.attributeFilter(attributeFilter)
                .and(PropertySpecifications.notDeleted())
                .and(PropertySpecifications.standalone())
        ).stream()
         .map(PropertyMapper::toPropertyResponse)
         .toList();
    }

}
