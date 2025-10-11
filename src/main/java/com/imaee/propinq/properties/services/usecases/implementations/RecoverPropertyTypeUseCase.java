package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.data.models.PropertyType;
import com.imaee.propinq.properties.data.repositories.IPropertyTypeRepository;
import com.imaee.propinq.properties.services.usecases.interfaces.IRecoverPropertyTypeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.properties.Constants.MSG_IS_NOT_DELETED;
import static com.imaee.propinq.properties.Constants.MSG_NOT_EXISTS;

@Component
@AllArgsConstructor
public class RecoverPropertyTypeUseCase implements IRecoverPropertyTypeUseCase {

    private final IPropertyTypeRepository propertyTypeRepository;

    @Override
    public void recoverPropertyType(UUID propertyTypeId) {
        PropertyType propertyType = findPropertyTypeByIdOrThrowException(propertyTypeId);
        throwExceptionIfPropertyTypeIsNotDeleted(propertyType);
        propertyType.setDeleted(false);
        propertyTypeRepository.save(propertyType);
    }

    private PropertyType findPropertyTypeByIdOrThrowException(UUID propertyTypeId) {
        return propertyTypeRepository.findById(propertyTypeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_NOT_EXISTS));
    }

    private void throwExceptionIfPropertyTypeIsNotDeleted(PropertyType propertyType) {
        if (!propertyType.getDeleted())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_IS_NOT_DELETED);
    }
}
