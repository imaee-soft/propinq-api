package com.imaee.propinq.properties.services.usecases.strategies;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.services.usecases.interfaces.IPropertyFilterStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class AllPropertiesStrategy implements IPropertyFilterStrategy {
    
    private final IPropertyRepository propertyRepository;
    
    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        return true;
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