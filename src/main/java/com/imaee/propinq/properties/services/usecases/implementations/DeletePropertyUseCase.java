package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.services.usecases.interfaces.IDeletePropertyUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IFindPropertyByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class DeletePropertyUseCase implements IDeletePropertyUseCase {

    private final IPropertyRepository propertyRepository;
    private final IFindPropertyByIdUseCase findPropertyByIdUseCase;

    @Override
    public void deleteProperty(UUID propertyId) {
        Property property = findPropertyByIdUseCase.findProperty(propertyId);
        property.setDeleted(true);
        propertyRepository.save(property);
    }
}
