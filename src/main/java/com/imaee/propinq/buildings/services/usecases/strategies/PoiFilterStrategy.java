package com.imaee.propinq.buildings.services.usecases.strategies;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.services.usecases.interfaces.IBuildingFilterStrategy;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsNearPoiUseCase;
import com.imaee.propinq.properties.controllers.requests.PoiFilterRequest;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("buildingPoiFilterStrategy")
@AllArgsConstructor
public class PoiFilterStrategy implements IBuildingFilterStrategy {

    private final IGetBuildingsNearPoiUseCase getBuildingsNearPoiUseCase;

    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        return filter.getPoi() != null && (filter.getPoi().getPoiType() != null || hasViewport(filter));
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public List<BuildingResponse> applyFilter(PropertyFilterRequest filter) {
        var poi = filter.getPoi();
        return getBuildingsNearPoiUseCase.getBuildingsNearPoi(
                poi.getPoiType(), poi.getRadiusKm(), poi.getNorth(), poi.getSouth(), poi.getEast(), poi.getWest(), poi.getLimit()
        );
    }

    @Override
    public Set<String> getHandledFields() {
        return Arrays.stream(PoiFilterRequest.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    private boolean hasViewport(PropertyFilterRequest filter) {
        var poi = filter.getPoi();
        return poi.getNorth() != null && poi.getSouth() != null && poi.getEast() != null && poi.getWest() != null;
    }
}
