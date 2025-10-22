package com.imaee.propinq.poi.services.usecases.implementations;

import com.imaee.propinq.poi.controllers.requests.PoiViewportRequest;
import com.imaee.propinq.poi.controllers.responses.PoiViewportResponse;
import com.imaee.propinq.poi.controllers.responses.PoiViewportResult;
import com.imaee.propinq.poi.data.models.Poi;
import com.imaee.propinq.poi.data.repositories.IPoiRepository;
import com.imaee.propinq.poi.mappers.PoiMapper;
import com.imaee.propinq.poi.services.usecases.interfaces.IGetPoisWithinViewportUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@AllArgsConstructor
public class GetPoisWhithinViewportUseCase implements IGetPoisWithinViewportUseCase {
    private final IPoiRepository poiRepository;
    private static final int DEFAULT_LIMIT = 500;
    private static final int MAX_LIMIT = 1000;
    @Override
    public PoiViewportResult getPoisWithinViewport(PoiViewportRequest poiViewportRequest) {
        validateBounds(poiViewportRequest.north(), poiViewportRequest.south(), poiViewportRequest.east(), poiViewportRequest.west());

        boolean crossesDateline = poiViewportRequest.west() > poiViewportRequest.east();
        var types = CollectionUtils.isEmpty(poiViewportRequest.types()) ? null : poiViewportRequest.types();

        int requestedLimit = poiViewportRequest.limit() != null ? poiViewportRequest.limit() : DEFAULT_LIMIT;
        int limit = Math.max(1, Math.min(requestedLimit, MAX_LIMIT));

        Pageable pageable = PageRequest.of(0, limit + 1);

        List<Poi> pois = poiRepository.findWithinViewport(
                poiViewportRequest.south(), poiViewportRequest.north(), poiViewportRequest.west(), poiViewportRequest.east(),
                crossesDateline, types, pageable
        );

        boolean hasMore = pois.size() > limit;
        if (hasMore) {
            pois = pois.subList(0, limit);
        }

        List<PoiViewportResponse> items = pois.stream()
                .map(PoiMapper::toPoiViewportResponse)
                .toList();
        return new PoiViewportResult(items, hasMore);
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
