package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.data.models.PropertyType;
import com.imaee.propinq.properties.data.repositories.IPropertyTypeRepository;
import com.imaee.propinq.properties.mappers.PropertyTypeMapper;
import com.imaee.propinq.properties.services.usecases.interfaces.IUpdatePropertyTypeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.properties.Constants.MSG_IS_DELETED;
import static com.imaee.propinq.properties.Constants.MSG_NOT_EXISTS;

@Component
@AllArgsConstructor
public class UpdatePropertyTypeUseCase implements IUpdatePropertyTypeUseCase {

    private final IPropertyTypeRepository propertyTypeRepository;

    @Override
    public PropertyTypeResponse updatePropertyType(UUID propertyTypeId, PropertyTypeRequest propertyTypeRequest) {
        PropertyType propertyType = findByIdOrThrowException(propertyTypeId);
        throwExceptionIfPropertyTypeIsDeleted(propertyType);
        propertyType.setName(propertyTypeRequest.name());
        propertyType.setDescription(propertyTypeRequest.description());
        return PropertyTypeMapper.toPropertyTypeResponse(propertyTypeRepository.save(propertyType));
    }

    @Override
    public PropertyType findByIdOrThrowException(UUID propertyTypeId) {
        return propertyTypeRepository.findById(propertyTypeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_NOT_EXISTS));
    }

    @Override
    public void throwExceptionIfPropertyTypeIsDeleted(PropertyType propertyType) {
        if (propertyType.getDeleted())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_IS_DELETED);
    }

}
