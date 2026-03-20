package com.imaee.propinq.buildings.services.usecases.strategies;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.usecases.interfaces.IBuildingFilterStrategy;
import com.imaee.propinq.properties.controllers.requests.AttributeFilterRequest;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.data.repositories.specifications.PropertySpecifications;
import com.imaee.propinq.shared.filters.AttributeFilterSupport;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("buildingAttributeFilterStrategy")
@AllArgsConstructor
public class AttributeFilterStrategy implements IBuildingFilterStrategy {

    private final IPropertyRepository propertyRepository;
    private final IBuildingRepository buildingRepository;

    @Override
    public boolean canHandle(PropertyFilterRequest filter) {
        return AttributeFilterSupport.hasAttributeFilters(filter);
    }

    @Override
    public int getPriority() {
        return 10; // baja prioridad
    }

    @Override
    public List<BuildingResponse> applyFilter(PropertyFilterRequest filter) {
        AttributeFilterRequest attributes = filter.getAttributes();
        Specification<Property> spec = Specification
                .where(PropertySpecifications.attributeFilter(attributes))
                .and(PropertySpecifications.notDeleted())
                .and(PropertySpecifications.inBuilding());

        List<Property> matchingProps = propertyRepository.findAll(spec);
        if (matchingProps.isEmpty()) return List.of();

        Set<UUID> buildingIds = matchingProps.stream()
                .map(p -> p.getBuilding() != null ? p.getBuilding().getBuildingId() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (buildingIds.isEmpty()) return List.of();

        Iterable<Building> buildings = buildingRepository.findAllById(buildingIds);
        return toResponses(buildings);
    }

    @Override
    public Set<String> getHandledFields() {
        return AttributeFilterSupport.handledFieldNames();
    }

    private List<BuildingResponse> toResponses(Iterable<Building> buildings) {
        List<BuildingResponse> list = new ArrayList<>();
        for (Building b : buildings) {
            if (!b.isDeleted()) {
                list.add(BuildingMapper.toBuildingResponse(b));
            }
        }
        return list;
    }
}
