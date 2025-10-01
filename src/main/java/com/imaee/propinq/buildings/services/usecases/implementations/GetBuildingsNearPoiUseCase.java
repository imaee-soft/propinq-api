package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsNearPoiUseCase;
import com.imaee.propinq.poi.data.enums.PoiType;
import com.imaee.propinq.poi.data.models.Poi;
import com.imaee.propinq.poi.data.repositories.IPoiRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class GetBuildingsNearPoiUseCase implements IGetBuildingsNearPoiUseCase {

    private final IBuildingRepository buildingRepository;
    private final IPoiRepository poiRepository;
    private static final int DEFAULT_LIMIT = 500;
    private static final int MAX_LIMIT = 1000;

    @Override
    public List<BuildingResponse> getBuildingsNearPoi(String poiType, Double radiusKm, Double north, Double south, Double east, Double west, Integer limit) {
        validateBounds(north, south, east, west);
        boolean crossesDateline = west > east;
        int requestedLimit = limit != null ? limit : DEFAULT_LIMIT;
        int maxResults = Math.max(1, Math.min(requestedLimit, MAX_LIMIT));

        Set<PoiType> types = (poiType == null || poiType.isEmpty())
                ? null
                : Set.of(PoiType.valueOf(poiType));

        List<Poi> pois = poiRepository.findWithinViewportAndTypes(
                south, north, west, east, crossesDateline, types,
                PageRequest.of(0, MAX_LIMIT)
        );

        Set<Building> result = new HashSet<>();
        for (Poi poi : pois) {
            List<Building> buildings = buildingRepository.findWithinRadiusAndBounds(
                    poi.getLatitude(), poi.getLongitude(),
                    radiusKm, south, north, west, east, crossesDateline,
                    PageRequest.of(0, maxResults)
            );
            result.addAll(buildings);
            if (result.size() >= maxResults) break;
        }

        return result.stream()
                .map(BuildingMapper::toBuildingResponse)
                .limit(maxResults)
                .toList();
    }

    private void validateBounds(double north, double south, double east, double west) {
        if (north < -90 || north > 90 || south < -90 || south > 90) {
            throw new IllegalArgumentException("Latitudes fuera de rango. Deben estar en [-90, 90].");
        }
        if (east < -180 || east > 180 || west < -180 || west > 180) {
            throw new IllegalArgumentException("Longitudes fuera de rango. Deben estar en [-180, 180].");
        }
        if (north < south) {
            throw new IllegalArgumentException("north debe ser >= south.");
        }
    }
}