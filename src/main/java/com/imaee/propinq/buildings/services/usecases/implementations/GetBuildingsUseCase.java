package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GetBuildingsUseCase implements IGetBuildingsUseCase {

    private final IBuildingRepository buildingRepository;

    @Override
    public List<BuildingResponse> getBuildings() {
        return buildingRepository.findAll()
                .stream()
                .map(BuildingMapper::toBuildingResponse)
                .toList();
    }
}