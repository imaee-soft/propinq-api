package com.imaee.propinq.properties.services.implementations;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.data.repositories.specifications.PropertySpecifications;
import com.imaee.propinq.properties.services.interfaces.IPropertyService;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertyUseCase;
import com.imaee.propinq.properties.services.facades.interfaces.IPropertyFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PropertyService implements IPropertyService {
    private final IGetPropertiesUseCase getPropertiesUseCase;
    private final IGetPropertyUseCase getPropertyUseCase;
    private final IPropertyRepository propertyRepository;
    private final IPropertyFacade propertyFacade;

    @Override
    public List<PropertyResponse> getProperties() {
        return getPropertiesUseCase.getProperties();
    }

    @Override
    public PropertyDetailsResponse getPropertyDetails(UUID propertyId) {
        return getPropertyUseCase.getPropertyDetails(propertyId);
    }

    @Override
    public List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId) {
        return getPropertiesUseCase.getBuildingProperties(buildingId);
    }

    @Override
    public List<PropertyDetailsResponse> filterProperties(PropertyFilterRequest filter) {
        List<Property> properties = propertyRepository.findAll(
            PropertySpecifications.propertyFilter(filter)
        );
        return properties.stream()
            .map(p -> PropertyMapper.toPropertyDetailsResponse(p, propertyFacade.getImagesURLs(p)))
            .toList();
    }
}
