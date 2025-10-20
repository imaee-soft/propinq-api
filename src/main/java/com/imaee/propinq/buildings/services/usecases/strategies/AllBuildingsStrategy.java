package com.imaee.propinq.buildings.services.usecases.strategies;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.usecases.interfaces.IBuildingFilterStrategy;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component("allBuildingsStrategy")
@AllArgsConstructor
public class AllBuildingsStrategy implements IBuildingFilterStrategy {

    private final IBuildingRepository buildingRepository;

    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        return true;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public List<BuildingResponse> applyFilter(PropertyFilterRequest filter) {
        return buildingRepository.findAllByDeletedFalse().stream()
                .map(BuildingMapper::toBuildingResponse)
                .toList();
    }

    @Override
    public Set<String> getHandledFields() {
        return Set.of();
    }
}
