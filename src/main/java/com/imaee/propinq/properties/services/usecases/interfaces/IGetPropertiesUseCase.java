package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.responses.PropertyResponse;

import java.util.List;

public interface  IGetPropertiesUseCase {
    List<PropertyResponse> getProperties();
}
