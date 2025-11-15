package com.imaee.propinq.properties.services.usecases.strategies;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesByAttributesUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IPropertyFilterStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.imaee.propinq.shared.filters.AttributeFilterSupport;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class AttributeFilterStrategy implements IPropertyFilterStrategy {
    
    private final IGetPropertiesByAttributesUseCase getPropertiesByAttributesUseCase;
    
    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        return AttributeFilterSupport.hasAttributeFilters(filter);
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
        return AttributeFilterSupport.handledFieldNames();
    }
}