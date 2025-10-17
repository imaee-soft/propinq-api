package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.requests.LocationFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesNearUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetPropertiesNearUseCase implements IGetPropertiesNearUseCase {

    private final IPropertyRepository propertyRepository;

    @Override
    public List<PropertyResponse> getPropertiesNear(LocationFilterRequest locationFilter) {
        List<Property> allProperties = propertyRepository.findAllByDeletedFalseAndBuildingIsNull();
        return allProperties.stream()
                .filter(property -> {
                    double dist = haversineDistance(
                        locationFilter.getLatitude(), 
                        locationFilter.getLongitude(), 
                        property.getLatitude(), 
                        property.getLongitude()
                    );
                    return dist <= locationFilter.getRadiusKm();
                })
                .map(PropertyMapper::toPropertyResponse)
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
