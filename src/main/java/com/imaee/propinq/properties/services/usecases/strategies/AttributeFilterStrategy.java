package com.imaee.propinq.properties.services.usecases.strategies;

import com.imaee.propinq.properties.controllers.requests.AttributeFilterRequest;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesByAttributesUseCase;
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
public class AttributeFilterStrategy implements IPropertyFilterStrategy {
    
    private final IGetPropertiesByAttributesUseCase getPropertiesByAttributesUseCase;
    
    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        return hasAttributeFilters(filter);
    }
    
    @Override
    public int getPriority() {
        return 10; // Baja prioridad - menos selectivo
    }
    
    @Override
    public List<PropertyResponse> applyFilter(PropertyFilterRequest filter) {
        return getPropertiesByAttributesUseCase.getPropertiesByAttributes(filter.getAttributes());
    }
    
    @Override
    public Set<String> getHandledFields() {
        // Obtener dinámicamente todos los campos de AttributeFilterRequest
        return Arrays.stream(AttributeFilterRequest.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toSet());
    }
    
    private boolean hasAttributeFilters(PropertyFilterRequest filter) {
        if (filter.getAttributes() == null) {
            return false;
        }
        
        // Verificar dinámicamente si algún campo de AttributeFilterRequest tiene valor
        return Arrays.stream(AttributeFilterRequest.class.getDeclaredFields())
            .anyMatch(field -> {
                try {
                    field.setAccessible(true);
                    Object value = field.get(filter.getAttributes());
                    return value != null;
                } catch (IllegalAccessException e) {
                    return false;
                }
            });
    }
}