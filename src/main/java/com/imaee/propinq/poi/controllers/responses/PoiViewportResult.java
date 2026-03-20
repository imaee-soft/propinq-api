package com.imaee.propinq.poi.controllers.responses;

import java.util.List;

public record PoiViewportResult(
        List<PoiViewportResponse> items,
        boolean hasMore
) {
}
