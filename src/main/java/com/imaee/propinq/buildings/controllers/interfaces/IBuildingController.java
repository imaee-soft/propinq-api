package com.imaee.propinq.buildings.controllers.interfaces;

import com.imaee.propinq.buildings.controllers.requests.BuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/buildings")
@Tag(
        name = "Buildings",
        description = "Operations for managing and querying buildings."
)
public interface IBuildingController {

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Saves a new building with name, description and address.")
    void saveBuilding(
            @RequestPart("building") @Valid BuildingRequest buildingRequest,
            @RequestPart("images") MultipartFile[] images
    );

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves a list of all buildings with basic information.")
    List<BuildingResponse> getBuildings();

    @GetMapping("/{buildingId}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves detailed information about a specific building by its ID.")
    BuildingDetailsResponse getBuilding(@PathVariable UUID buildingId);
}
