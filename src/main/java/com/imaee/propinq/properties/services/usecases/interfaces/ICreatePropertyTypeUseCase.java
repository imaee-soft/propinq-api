package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;

public interface ICreatePropertyTypeUseCase {
    PropertyTypeResponse createPropertyType(PropertyTypeRequest propertyTypeRequest);
}
