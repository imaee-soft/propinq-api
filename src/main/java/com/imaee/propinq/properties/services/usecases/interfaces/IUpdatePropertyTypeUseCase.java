package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.data.models.PropertyType;

import java.util.UUID;

public interface IUpdatePropertyTypeUseCase {
    PropertyTypeResponse updatePropertyType(UUID propertyTypeId, PropertyTypeRequest propertyTypeRequest);
    PropertyType findByIdOrThrowException(UUID propertyTypeId);
    void throwExceptionIfPropertyTypeIsDeleted(PropertyType propertyType);
}
