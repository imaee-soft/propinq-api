package com.imaee.propinq.poi.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.poi.controllers.responses.PoiViewportResponse;
import com.imaee.propinq.poi.data.enums.PoiType;
import com.imaee.propinq.poi.data.models.Poi;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class PoiMapper {
    private PoiMapper() {}

    public static PoiType classifyType(JsonNode props) {
        String[][] pairs = new String[][]{
                {"amenity", "AMENITY"}, {"shop", "SHOP"}, {"tourism", "TOURISM"}, {"leisure", "LEISURE"},
                {"historic", "HISTORIC"}, {"man_made", "MAN_MADE"}, {"natural", "NATURAL"}, {"sport", "SPORT"},
                {"craft", "CRAFT"}, {"office", "OFFICE"}, {"emergency", "EMERGENCY"}, {"healthcare", "HEALTHCARE"},
                {"public_transport", "PUBLIC_TRANSPORT"}, {"railway", "RAILWAY"}, {"aeroway", "AEROWAY"},
                {"aerialway", "AERIALWAY"}, {"power", "POWER"}, {"waterway", "WATERWAY"}
        };
        for (String[] p : pairs) {
            var node = props.get(p[0]);
            if (node != null && !node.isNull() && !node.asText().isBlank()) {
                return PoiType.valueOf(p[1]);
            }
        }
        var h = props.get("highway");
        if (h != null && !h.isNull()) {
            String hv = h.asText();
            if ("bus_stop".equals(hv) || "platform".equals(hv) || "rest_area".equals(hv)) {
                return PoiType.HIGHWAY;
            }
        }
        return null;
    }


    public static PoiViewportResponse toPoiViewportResponse(Poi poi) {
        return new PoiViewportResponse(
                poi.getPoiId(),
                poi.getType(),
                poi.getName(),
                poi.getLatitude(),
                poi.getLongitude()
        );
    }
}
