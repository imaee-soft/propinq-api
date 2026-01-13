package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.data.models.Property;

import java.util.UUID;

public interface IFindPropertyByIdUseCase {
    Property findProperty(UUID propertyId);
}