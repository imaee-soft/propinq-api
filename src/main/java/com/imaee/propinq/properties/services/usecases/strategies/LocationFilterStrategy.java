package com.imaee.propinq.properties.services.usecases.strategies;

import com.imaee.propinq.properties.controllers.requests.LocationFilterRequest;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesNearUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IPropertyFilterStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class LocationFilterStrategy implements IPropertyFilterStrategy {
    
    private final IGetPropertiesNearUseCase getPropertiesNearUseCase;
    
    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        return filter.getLocation() != null &&
               filter.getLocation().getLatitude() != null && 
               filter.getLocation().getLongitude() != null && 
               filter.getLocation().getRadiusKm() != null;
    }
    
    @Override
    public int getPriority() {
        return 90; // Alta prioridad - selectivo
    }
    
    @Override
    public List<PropertyResponse> applyFilter(PropertyFilterRequest filter) {
        return getPropertiesNearUseCase.getPropertiesNear(filter.getLocation());
    }
    
    @Override
    public Set<String> getHandledFields() {
        // Obtener dinámicamente todos los campos de LocationFilterRequest
        return Arrays.stream(LocationFilterRequest.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toSet());
    }
}