package com.imaee.propinq.buildings.services.implementations;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.interfaces.IBuildingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BuildingService implements IBuildingService {
    private final IBuildingRepository buildingRepository;

    public BuildingService( IBuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public List<BuildingResponse> getBuildings() {

        return buildingRepository.findAll()
                .stream()
                .map(BuildingMapper::toBuildingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BuildingResponse getBuilding(UUID buildingId) {
       return BuildingMapper.toBuildingResponse(findBuildingByIdOrThrowException(buildingId));
    }
    private Building findBuildingByIdOrThrowException(UUID buildingId) {
        return buildingRepository.findById(buildingId)
                .orElseThrow(() -> new NoSuchElementException("Building with id " + buildingId + " does not exist." ));
    }
}
