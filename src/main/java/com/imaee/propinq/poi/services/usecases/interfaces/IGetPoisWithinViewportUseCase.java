package com.imaee.propinq.poi.services.usecases.interfaces;

import com.imaee.propinq.poi.controllers.requests.PoiViewportRequest;
import com.imaee.propinq.poi.controllers.responses.PoiViewportResponse;
import com.imaee.propinq.poi.controllers.responses.PoiViewportResult;

public interface IGetPoisWithinViewportUseCase {

    PoiViewportResult getPoisWithinViewport(PoiViewportRequest poiViewportRequest);
}
