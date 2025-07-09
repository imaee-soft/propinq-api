package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

import static com.imaee.propinq.buildings.Constants.NO_BUILDING_MESSAGE;

@Component
@AllArgsConstructor
public class FindBuildingByIdUseCase implements IFindBuildingByIdUseCase {

    private final IBuildingRepository buildingRepository;

    @Override
    public Building findBuilding(UUID buildingId) {
        return buildingRepository.findById(buildingId)
                .orElseThrow(() -> new NoSuchElementException(NO_BUILDING_MESSAGE));
    }
}