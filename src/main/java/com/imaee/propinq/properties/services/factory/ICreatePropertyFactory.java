package com.imaee.propinq.properties.services.factory;

import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.services.usecases.interfaces.ICreatePropertyUseCase;

public interface ICreatePropertyFactory {
    ICreatePropertyUseCase provideCreatePropertyUseCase(CreatePropertyRequest request);
}