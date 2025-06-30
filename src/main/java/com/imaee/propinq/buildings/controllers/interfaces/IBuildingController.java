package com.imaee.propinq.buildings.controllers.interfaces;


import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/buildings")
@Tag(
        name = "Buildings",
        description = "Operations for managing and querying buildings."
)
public interface IBuildingController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves a list of all buildings with basic information.")
    List<BuildingResponse> getBuildings();

    @GetMapping("/{buildingId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves detailed information about a specific building by its ID.")
    BuildingDetailsResponse getBuilding(@PathVariable UUID buildingId);
}
