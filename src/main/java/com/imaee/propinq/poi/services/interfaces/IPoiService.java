package com.imaee.propinq.poi.services.interfaces;

import com.imaee.propinq.poi.controllers.requests.PoiViewportRequest;
import com.imaee.propinq.poi.controllers.responses.PoiViewportResult;

public interface IPoiService {

    PoiViewportResult getPoisWithinViewport(PoiViewportRequest poiViewportRequest);
}
