package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.services.usecases.interfaces.IFindPropertyByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.properties.Constants.NO_PROPERTY_MESSAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Component
@AllArgsConstructor
public class FindPropertyByIdUseCase implements IFindPropertyByIdUseCase {
    private final IPropertyRepository propertyRepository;

    @Override
    public Property findProperty(UUID propertyId) {
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, NO_PROPERTY_MESSAGE));
    }
}
