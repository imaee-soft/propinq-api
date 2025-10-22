package com.imaee.propinq.properties.services.factory;

import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.services.usecases.implementations.CreateApartmentUseCase;
import com.imaee.propinq.properties.services.usecases.implementations.CreateHouseUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.ICreatePropertyUseCase;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.imaee.propinq.properties.Constants.UNSUPPORTED_PROPERTY_TYPE;
import static com.imaee.propinq.properties.data.enums.PropertyType.APARTAMENTO;

@Component
@AllArgsConstructor
public class CreatePropertyFactory implements ICreatePropertyFactory {

    private static final String APARTMENT_TYPE = APARTAMENTO.name();
    private static final String HOUSE_TYPE = APARTAMENTO.name();
    private final ApplicationContext applicationContext;

    @Override
    public ICreatePropertyUseCase provideCreatePropertyUseCase(CreatePropertyRequest request) {
        if (APARTMENT_TYPE.equals(request.type()))
            return applicationContext.getBean(CreateApartmentUseCase.class);
        if (HOUSE_TYPE.equals(request.type()))
            return applicationContext.getBean(CreateHouseUseCase.class);
        throw new RuntimeException(UNSUPPORTED_PROPERTY_TYPE);
    }
}