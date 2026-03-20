package com.imaee.propinq.poi.controllers.implementations;

import com.imaee.propinq.poi.controllers.interfaces.IPoiController;
import com.imaee.propinq.poi.controllers.requests.PoiViewportRequest;
import com.imaee.propinq.poi.controllers.responses.PoiViewportResult;
import com.imaee.propinq.poi.services.interfaces.IPoiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PoiController implements IPoiController {

    private final IPoiService poiService;

    @Override
    public PoiViewportResult getPoisWithin(PoiViewportRequest poiViewportRequest) {
        return poiService.getPoisWithinViewport(poiViewportRequest);
    }
}