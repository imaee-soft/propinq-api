package com.imaee.propinq.properties.services.usecases.interfaces;

import java.util.List;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;

public interface IGetPropertiesByAttributesUseCase {
    List<PropertyResponse> getPropertiesByAttributes(PropertyFilterRequest filter);
}
