package com.imaee.propinq.properties.services.usecases.strategies;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.services.usecases.interfaces.IPropertyFilterStrategy;
import com.imaee.propinq.shared.filters.AttributeFilterSupport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class AllPropertiesStrategy implements IPropertyFilterStrategy {
    
    private final IPropertyRepository propertyRepository;
    
    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        // Solo como fallback cuando no hay ningún filtro activo (parcial o completo).
        return !hasActiveFilters(filter);
    }

    private boolean hasActiveFilters(PropertyFilterRequest filter) {
        if (filter == null) {
            return false;
        }
        if (AttributeFilterSupport.hasAttributeFilters(filter)) {
            return true;
        }
        return hasAnyNonNullField(filter.getLocation()) || hasAnyNonNullField(filter.getPoi());
    }

    private boolean hasAnyNonNullField(Object target) {
        if (target == null) {
            return false;
        }
        for (Field field : target.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(target) != null) {
                    return true;
                }
            } catch (IllegalAccessException ignored) {
                // ignore reflective access issues and keep scanning
            }
        }
        return false;
    }
    
    @Override
    public int getPriority() {
        return 1; // Prioridad más baja - solo si no hay otros filtros
    }
    
    @Override
    public List<PropertyResponse> applyFilter(PropertyFilterRequest filter) {
        return propertyRepository.findAllByDeletedFalseAndBuildingIsNull()
                .stream()
                .map(PropertyMapper::toPropertyResponse)
                .toList();
    }
    
    @Override
    public Set<String> getHandledFields() {
        return Set.of();
    }
}