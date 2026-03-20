package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;

import java.util.UUID;

public interface IGetPropertyUseCase {
    PropertyDetailsResponse getPropertyDetails(UUID propertyId);
}
