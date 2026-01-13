package com.imaee.propinq.poi.controllers.interfaces;

import com.imaee.propinq.poi.controllers.requests.PoiViewportRequest;
import com.imaee.propinq.poi.controllers.responses.PoiViewportResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/pois")
@Tag(
        name = "Pois",
        description = "Operations for managing and querying Points of Interest."
)
public interface IPoiController {

    @GetMapping("/within")
    @ResponseStatus(OK)
    @Operation(
            summary = "Obtener TODOS los POIs dentro del viewport (bounding box)",
            description = "Devuelve todos los POIs contenidos en el rectángulo definido por north, south, east, west (WGS84). " +
                    "Maneja cruce del antimeridiano (cuando west > east). Se aplica un límite servidor configurable para proteger el backend. " +
                    "Si hay más que el límite, hasMore=true.",
            parameters = {
                    @Parameter(name = "north", description = "Latitud norte (máxima). Rango [-90, 90].", required = true, in = ParameterIn.QUERY),
                    @Parameter(name = "south", description = "Latitud sur (mínima). Rango [-90, 90].", required = true, in = ParameterIn.QUERY),
                    @Parameter(name = "east", description = "Longitud este (derecha). Rango [-180, 180].", required = true, in = ParameterIn.QUERY),
                    @Parameter(name = "west", description = "Longitud oeste (izquierda). Rango [-180, 180].", required = true, in = ParameterIn.QUERY),
                    @Parameter(name = "limit", description = "Límite máximo de POIs a devolver (opcional). Se ajusta a un tope de servidor.", required = false, in = ParameterIn.QUERY),
                    @Parameter(name = "types", description = "Tipo de POI (opcional). Ej: AMENITY", required = false, in = ParameterIn.QUERY),
                    @Parameter(name = "zoom", description = "Nivel de zoom actual (opcional, reservado para clustering futuro).", required = false, in = ParameterIn.QUERY),
            }
    )
    PoiViewportResult getPoisWithin(
            @ParameterObject @ModelAttribute PoiViewportRequest request
    );
}

