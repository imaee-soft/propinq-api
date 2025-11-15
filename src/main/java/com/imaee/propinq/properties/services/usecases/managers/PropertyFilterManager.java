package com.imaee.propinq.properties.services.usecases.managers;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.usecases.interfaces.IPropertyFilterStrategy;
import com.imaee.propinq.properties.services.usecases.managers.interfaces.IPropertyFilterManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PropertyFilterManager implements IPropertyFilterManager {
    
    private final List<IPropertyFilterStrategy> filterStrategies;
    
    /**
     * Aplica filtros de forma inteligente, seleccionando la estrategia más eficiente
     */
    public List<PropertyResponse> applyFilters(PropertyFilterRequest filter) {
        // Encontrar todas las estrategias que pueden manejar el filtro
        List<IPropertyFilterStrategy> applicableStrategies = filterStrategies.stream()
            .filter(strategy -> strategy.canHandle(filter))
            .sorted(Comparator.comparing(IPropertyFilterStrategy::getPriority).reversed()) // Mayor prioridad primero
            .toList();
        
        if (applicableStrategies.isEmpty()) {
            throw new IllegalArgumentException("No hay estrategia disponible para el filtro proporcionado");
        }
        
        // Si hay múltiples filtros activos, usar combinación de estrategias
        Set<String> activeFields = getActiveFields(filter);
        if (needsMultipleStrategies(activeFields, applicableStrategies)) {
            return applyCombinedFilters(filter, applicableStrategies);
        }
        
        // Si una sola estrategia puede manejar todo, usarla
        return applicableStrategies.get(0).applyFilter(filter);
    }
    
    /**
     * Determina si se necesitan múltiples estrategias para cubrir todos los campos activos
     */
    private boolean needsMultipleStrategies(Set<String> activeFields, List<IPropertyFilterStrategy> strategies) {
        // Si alguna estrategia puede manejar todos los campos activos, no necesitamos múltiples
        return strategies.stream()
            .noneMatch(strategy -> strategy.getHandledFields().containsAll(activeFields));
    }
    
    /**
     * Aplica múltiples estrategias y combina resultados usando intersección
     */
    private List<PropertyResponse> applyCombinedFilters(PropertyFilterRequest filter, List<IPropertyFilterStrategy> strategies) {
        Set<UUID> resultIds = null;
        List<PropertyResponse> finalResults = null;
        
        for (IPropertyFilterStrategy strategy : strategies) {
            List<PropertyResponse> strategyResults = strategy.applyFilter(filter);
            Set<UUID> strategyIds = strategyResults.stream()
                .map(PropertyResponse::propertyId)
                .collect(Collectors.toSet());
            
            if (resultIds == null) {
                // Primera estrategia - inicializar resultados
                resultIds = new HashSet<>(strategyIds);
                finalResults = strategyResults;
            } else {
                // Estrategias subsecuentes - intersección
                resultIds.retainAll(strategyIds);
            }
        }

        final Set<UUID> finalResultIds = resultIds;
        
        // Filtrar resultados finales manteniendo solo los IDs que pasaron todos los filtros
        return finalResults != null ? 
            finalResults.stream()
                .filter(result -> finalResultIds != null && finalResultIds.contains(result.propertyId()))
                .toList() : 
            List.of();
    }
    
    // Obtiene los nombres de campos que tienen valores no-null en el filtro usando reflection
    private Set<String> getActiveFields(PropertyFilterRequest filter) {
        if (filter == null) {
            return Set.of();
        }
        
        Set<String> activeFields = new HashSet<>();
        
        try {
            // Examinar campos de AttributeFilterRequest
            if (filter.getAttributes() != null) {
                Field[] attributeFields = filter.getAttributes().getClass().getDeclaredFields();
                for (Field field : attributeFields) {
                    field.setAccessible(true);
                    Object value = field.get(filter.getAttributes());
                    if (value != null) {
                        activeFields.add(field.getName());
                    }
                }
            }
            
            // Examinar campos de LocationFilterRequest
            if (filter.getLocation() != null) {
                Field[] locationFields = filter.getLocation().getClass().getDeclaredFields();
                for (Field field : locationFields) {
                    field.setAccessible(true);
                    Object value = field.get(filter.getLocation());
                    if (value != null) {
                        activeFields.add(field.getName());
                    }
                }
            }
            
            // Examinar campos de PoiFilterRequest
            if (filter.getPoi() != null) {
                Field[] poiFields = filter.getPoi().getClass().getDeclaredFields();
                for (Field field : poiFields) {
                    field.setAccessible(true);
                    Object value = field.get(filter.getPoi());
                    if (value != null) {
                        activeFields.add(field.getName());
                    }
                }
            }
            
        } catch (IllegalAccessException e) {
            // En caso de error, asumir que no hay campos activos
            return Set.of();
        }
        
        return activeFields;
    }
}