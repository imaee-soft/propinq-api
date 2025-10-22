package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.services.usecases.interfaces.IFindPropertyByIdUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IRestorePropertyUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RestorePropertyUseCase implements IRestorePropertyUseCase {
    private final IPropertyRepository propertyRepository;
    private final IFindPropertyByIdUseCase findPropertyByIdUseCase;

    @Override
    public void restoreProperty(UUID propertyId) {
        Property property = findPropertyByIdUseCase.findProperty(propertyId);
        property.setDeleted(false);
        propertyRepository.save(property);
    }
}
