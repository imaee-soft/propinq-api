package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.data.models.PropertyType;
import com.imaee.propinq.properties.data.repositories.IPropertyTypeRepository;
import com.imaee.propinq.properties.services.usecases.interfaces.IDeletePropertyTypeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class DeletePropertyTypeUseCase implements IDeletePropertyTypeUseCase {

    private final IPropertyTypeRepository propertyTypeRepository;
    private final UpdatePropertyTypeUseCase updatePropertyTypeUseCase;

    @Override
    public void deletePropertyType(UUID propertyTypeId) {
        PropertyType propertyType = updatePropertyTypeUseCase.findByIdOrThrowException(propertyTypeId);
        updatePropertyTypeUseCase.throwExceptionIfPropertyTypeIsDeleted(propertyType);
        propertyType.setDeleted(true);
        propertyTypeRepository.save(propertyType);
    }
}
