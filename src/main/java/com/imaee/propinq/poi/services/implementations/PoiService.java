package com.imaee.propinq.poi.services.implementations;

import com.imaee.propinq.poi.controllers.requests.PoiViewportRequest;
import com.imaee.propinq.poi.controllers.responses.PoiViewportResult;
import com.imaee.propinq.poi.services.interfaces.IPoiService;
import com.imaee.propinq.poi.services.usecases.interfaces.IGetPoisWithinViewportUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PoiService implements IPoiService {
    private final IGetPoisWithinViewportUseCase getPoisWhithinViewPortUseCase;

    @Override
    public PoiViewportResult getPoisWithinViewport(PoiViewportRequest poiViewportRequest) {
        return getPoisWhithinViewPortUseCase.getPoisWithinViewport(poiViewportRequest);
    }
}
