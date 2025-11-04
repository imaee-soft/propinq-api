package com.imaee.propinq.properties.services.implementations;

import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.controllers.requests.UpdatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.factory.ICreatePropertyFactory;
import com.imaee.propinq.properties.services.interfaces.IPropertyService;
import com.imaee.propinq.properties.services.usecases.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PropertyService implements IPropertyService {
    private final IGetPropertiesUseCase getPropertiesUseCase;
    private final IGetPropertyUseCase getPropertyUseCase;
    private final ICreatePropertyFactory createPropertyFactory;
    private final IGetPropertiesNearUseCase getPropertiesNearUseCase;
    private final IGetPropertiesNearPoiUseCase getPropertiesNearPoiUseCase;
    private final IUpdatePropertyUseCase updatePropertyUseCase;
    private final IDeletePropertyUseCase deletePropertyUseCase;
    private final IRestorePropertyUseCase restorePropertyUsecase;

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
    public Page<PropertyDetailsResponse> getPropertiesDetails(int page, int size) {
        return getPropertiesUseCase.getPropertiesDetails(page, size);
    }

    @Override
    public void createProperty(CreatePropertyRequest request, MultipartFile[] imageFiles) {
        createPropertyFactory.provideCreatePropertyUseCase(request)
                .createProperty(request, imageFiles);
    }

    @Override
    public List<PropertyResponse> getPropertiesNear(Double latitude, Double longitude, Double radiusKm) {
        return getPropertiesNearUseCase.getPropertiesNear(latitude, longitude, radiusKm);
    }

    @Override
    public List<PropertyResponse> getPropertiesNearPoi(String poiType, Double radiusKm, Double north,
                                                       Double south, Double east, Double west, Integer limit) {
        return getPropertiesNearPoiUseCase.getPropertiesNearPoi(poiType, radiusKm, north, south, east, west, limit);
    }

    @Override
    public PropertyDetailsResponse updateProperty(UUID propertyId, UpdatePropertyRequest updatePropertyRequest, MultipartFile[] imageFiles) {
        return updatePropertyUseCase.updateProperty(propertyId, updatePropertyRequest, imageFiles);
    }
    @Override
    public void deleteProperty(UUID propertyId) {
        deletePropertyUseCase.deleteProperty(propertyId);
    }

    @Override
    public void restoreProperty(UUID propertyId) {
        restorePropertyUsecase.restoreProperty(propertyId);
    }
}
