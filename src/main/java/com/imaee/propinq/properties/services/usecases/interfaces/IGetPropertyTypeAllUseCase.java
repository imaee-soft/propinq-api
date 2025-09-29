package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;

import java.util.List;

public interface IGetPropertyTypeAllUseCase {
    List<PropertyTypeResponse> getPropertyTypeAll();
}
