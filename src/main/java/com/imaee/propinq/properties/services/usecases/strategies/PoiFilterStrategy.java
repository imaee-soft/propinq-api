package com.imaee.propinq.properties.services.usecases.strategies;

import com.imaee.propinq.properties.controllers.requests.PoiFilterRequest;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesNearPoiUseCase;
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
public class PoiFilterStrategy implements IPropertyFilterStrategy {
    
    private final IGetPropertiesNearPoiUseCase getPropertiesNearPoiUseCase;
    
    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        return filter.getPoi() != null && 
               (filter.getPoi().getPoiType() != null || hasViewportFilter(filter));
    }
    
    @Override
    public int getPriority() {
        return 100; // Máxima prioridad - más selectivo
    }
    
    @Override
    public List<PropertyResponse> applyFilter(PropertyFilterRequest filter) {
        return getPropertiesNearPoiUseCase.getPropertiesNearPoi(filter.getPoi());
    }
    
    @Override
    public Set<String> getHandledFields() {
        // Obtener dinámicamente todos los campos de PoiFilterRequest
        return Arrays.stream(PoiFilterRequest.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toSet());
    }
    
    private boolean hasViewportFilter(PropertyFilterRequest filter) {
        PoiFilterRequest poi = filter.getPoi();
        return poi != null &&
               poi.getNorth() != null && 
               poi.getSouth() != null && 
               poi.getEast() != null && 
               poi.getWest() != null;
    }
}