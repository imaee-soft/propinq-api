package com.imaee.propinq.poi.controllers.requests;

import com.imaee.propinq.poi.data.enums.PoiType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record PoiViewportRequest(
        @Schema(description = "Latitud norte (máxima).", requiredMode = Schema.RequiredMode.REQUIRED)
        double north,
        @Schema(description = "Latitud sur (mínima).", requiredMode = Schema.RequiredMode.REQUIRED)
        double south,
        @Schema(description = "Longitud este (derecha).", requiredMode = Schema.RequiredMode.REQUIRED)
        double east,
        @Schema(description = "Longitud oeste (izquierda).", requiredMode = Schema.RequiredMode.REQUIRED)
        double west,
        @Schema(description = "Tipos de POI a filtrar (opcional).")
        Set<PoiType> types,
        @Schema(description = "Límite máximo de POIs a devolver (opcional).")
        Integer limit,
        @Schema(description = "Nivel de zoom actual (opcional, reservado para clustering futuro).")
        Integer zoom
) {
}
