package com.imaee.propinq.properties.services.implementations;

import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.interfaces.IPropertyService;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesNearUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertyUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PropertyService implements IPropertyService {
    private final IGetPropertiesUseCase getPropertiesUseCase;
    private final IGetPropertyUseCase getPropertyUseCase;
    private final IGetPropertiesNearUseCase getPropertiesNearUseCase;

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
    public List<PropertyResponse> getPropertiesNear( Double latitude,  Double longitude, Double radiusKm) {
        return getPropertiesNearUseCase.getPropertiesNear(latitude, longitude, radiusKm);
    }
}
