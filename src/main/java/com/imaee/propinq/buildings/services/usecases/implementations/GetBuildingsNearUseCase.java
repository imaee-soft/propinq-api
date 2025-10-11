package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsNearUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import com.imaee.propinq.buildings.mappers.BuildingMapper;

@Component
@AllArgsConstructor
public class GetBuildingsNearUseCase implements IGetBuildingsNearUseCase {

    private final IBuildingRepository buildingRepository;

    @Override
    public List<BuildingResponse> getBuildingsNear(Double latitude, Double longitude, Double radiusKm) {
        List<Building> allBuildings = buildingRepository.findAll();
        return allBuildings.stream()
                .filter(building -> {
                    double dist = haversineDistance(latitude, longitude, building.getLatitude(), building.getLongitude());
                    return dist <= radiusKm;
                })
                .map(BuildingMapper::toBuildingResponse)
                .collect(Collectors.toList());
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
