package com.imaee.propinq.buildings.services.usecases.managers;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.services.usecases.interfaces.IBuildingFilterStrategy;
import com.imaee.propinq.buildings.services.usecases.managers.interfaces.IBuildingFilterManager;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BuildingFilterManager implements IBuildingFilterManager {

    private final List<IBuildingFilterStrategy> filterStrategies;

    @Override
    public List<BuildingResponse> applyFilters(PropertyFilterRequest filter) {
        List<IBuildingFilterStrategy> applicable = filterStrategies.stream()
                .filter(s -> s.canHandle(filter))
                .sorted(Comparator.comparing(IBuildingFilterStrategy::getPriority).reversed())
                .toList();

        if (applicable.isEmpty()) {
            throw new IllegalArgumentException("No hay estrategia disponible para el filtro proporcionado (buildings)");
        }

        Set<String> active = getActiveFields(filter);
        boolean needsCombine = applicable.stream()
                .noneMatch(s -> s.getHandledFields().containsAll(active));

        if (!needsCombine) {
            return applicable.get(0).applyFilter(filter);
        }

        Set<UUID> ids = null;
        List<BuildingResponse> finalResults = null;
        for (IBuildingFilterStrategy s : applicable) {
            List<BuildingResponse> part = s.applyFilter(filter);
            Set<UUID> partIds = part.stream().map(BuildingResponse::buildingId).collect(Collectors.toSet());
            if (ids == null) {
                ids = new HashSet<>(partIds);
                finalResults = part;
            } else {
                ids.retainAll(partIds);
            }
        }

        Set<UUID> finalIds = ids != null ? ids : Set.of();
        return finalResults != null ? finalResults.stream()
                .filter(r -> finalIds.contains(r.buildingId()))
                .toList() : List.of();
    }

    private Set<String> getActiveFields(PropertyFilterRequest filter) {
        if (filter == null) return Set.of();
        Set<String> active = new HashSet<>();
        try {
            if (filter.getAttributes() != null) {
                for (Field f : filter.getAttributes().getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    if (f.get(filter.getAttributes()) != null) active.add(f.getName());
                }
            }
            if (filter.getLocation() != null) {
                for (Field f : filter.getLocation().getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    if (f.get(filter.getLocation()) != null) active.add(f.getName());
                }
            }
            if (filter.getPoi() != null) {
                for (Field f : filter.getPoi().getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    if (f.get(filter.getPoi()) != null) active.add(f.getName());
                }
            }
        } catch (IllegalAccessException ignored) {
        }
        return active;
    }
}
