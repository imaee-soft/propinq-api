package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.data.repositories.IPropertyTypeRepository;
import com.imaee.propinq.properties.mappers.PropertyTypeMapper;
import com.imaee.propinq.properties.services.usecases.interfaces.ICreatePropertyTypeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.imaee.propinq.properties.Constants.MSG_ALREADY_EXISTS;

@Component
@AllArgsConstructor
public class CreatePropertyTypeUseCase implements ICreatePropertyTypeUseCase {

    private final IPropertyTypeRepository propertyTypeRepository;

    @Override
    public PropertyTypeResponse createPropertyType(PropertyTypeRequest propertyTypeRequest) {
        throwExceptionIfPropertyTypeAlreadyExists(propertyTypeRequest.name());
        final var propertyType = PropertyTypeMapper.toPropertyType(propertyTypeRequest);
        return PropertyTypeMapper.toPropertyTypeResponse(
                propertyTypeRepository.save(propertyType)
        );
    }
    private void throwExceptionIfPropertyTypeAlreadyExists(String propertyTypeName) {
        if (propertyTypeRepository.existsByNameAndDeletedFalse(propertyTypeName)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_ALREADY_EXISTS);
        }
    }
}
