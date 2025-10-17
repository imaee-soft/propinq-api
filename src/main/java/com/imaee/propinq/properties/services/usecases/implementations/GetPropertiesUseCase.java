package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.facades.interfaces.IPropertyFacade;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesUseCase;
import com.imaee.propinq.properties.services.usecases.managers.interfaces.IPropertyFilterManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GetPropertiesUseCase implements IGetPropertiesUseCase {
    private final IPropertyRepository propertyRepository;
    private final IPropertyFacade propertyFacade;
    private final IPropertyFilterManager filterManager;

    @Override
    public List<PropertyResponse> getProperties(PropertyFilterRequest filter) {
        return filterManager.applyFilters(filter);
    }

    @Override
    public List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId) {
        return propertyRepository.findAllByDeletedFalseAndBuilding_BuildingId(buildingId)
                .stream()
                .map(p -> PropertyMapper.toPropertyDetailsResponse(p, propertyFacade.getImagesURLs(p)))
                .toList();
    }
}
