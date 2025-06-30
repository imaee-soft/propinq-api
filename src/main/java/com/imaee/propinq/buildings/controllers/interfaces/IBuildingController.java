package com.imaee.propinq.buildings.controllers.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/buildings")
@Tag(name = "Buildings", description = "Operaciones relacionadas con la gestión de edificios")
public interface IBuildingController {

    @Operation(
        summary = "Obtener todos los edificios",
        description = "Retorna una lista de todos los edificios disponibles en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de edificios obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping
    List<BuildingResponse> getBuildings();

    @Operation(
        summary = "Obtener edificio por ID",
        description = "Retorna la información detallada de un edificio específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Edificio encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Edificio no encontrado", content = @Content()),
        @ApiResponse(responseCode = "400", description = "ID de edificio inválido", content = @Content())
    })
    @GetMapping("/{buildingId}")
    BuildingResponse getBuilding(@Parameter(description = "ID del edificio a buscar") @PathVariable UUID buildingId);
}
