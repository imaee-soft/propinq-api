package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.services.usecases.interfaces.IExistsApartmentNumberUseCase;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ExistsApartmentNumberUseCase implements IExistsApartmentNumberUseCase {

    private final IPropertyRepository propertyRepository;

    @Override
    public boolean existsApartmentNumber(UUID buildingId, String number) {
        return propertyRepository.existsByApartmentNumberAndBuildingBuildingId(number, buildingId);
    }
}