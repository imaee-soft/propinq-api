package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.poi.data.enums.PoiType;
import com.imaee.propinq.poi.data.models.Poi;
import com.imaee.propinq.poi.data.repositories.IPoiRepository;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesNearPoiUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class GetPropertiesNearPoiUseCase implements IGetPropertiesNearPoiUseCase {

    private final IPropertyRepository propertyRepository;
    private final IPoiRepository poiRepository;
    private static final int DEFAULT_LIMIT = 500;
    private static final int MAX_LIMIT = 1000;

    @Override
    public List<PropertyResponse> getPropertiesNearPoi(String poiType, Double radiusKm, Double north,
                                                Double south, Double east, Double west, Integer limit) {
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

        Set<Property> result = new HashSet<>();
        for (Poi poi : pois) {
            List<Property> properties = propertyRepository.findWithinRadiusAndBounds(
                    poi.getLatitude(), poi.getLongitude(),
                    radiusKm, south, north, west, east, crossesDateline,
                    PageRequest.of(0, maxResults)
            );
            result.addAll(properties);
            if (result.size() >= maxResults) break;
        }

        return result.stream()
                .map(PropertyMapper::toPropertyResponse)
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
