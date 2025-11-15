package com.imaee.propinq.buildings.services.usecases.strategies;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.services.usecases.interfaces.IBuildingFilterStrategy;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsNearUseCase;
import com.imaee.propinq.properties.controllers.requests.LocationFilterRequest;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("buildingLocationFilterStrategy")
@AllArgsConstructor
public class LocationFilterStrategy implements IBuildingFilterStrategy {

    private final IGetBuildingsNearUseCase getBuildingsNearUseCase;

    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        return filter.getLocation() != null &&
                filter.getLocation().getLatitude() != null &&
                filter.getLocation().getLongitude() != null &&
                filter.getLocation().getRadiusKm() != null;
    }

    @Override
    public int getPriority() {
        return 90;
    }

    @Override
    public List<BuildingResponse> applyFilter(PropertyFilterRequest filter) {
        var loc = filter.getLocation();
        return getBuildingsNearUseCase.getBuildingsNear(loc.getLatitude(), loc.getLongitude(), loc.getRadiusKm());
    }

    @Override
    public Set<String> getHandledFields() {
        return Arrays.stream(LocationFilterRequest.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());
    }
}
