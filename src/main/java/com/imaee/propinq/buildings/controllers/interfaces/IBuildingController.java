package com.imaee.propinq.buildings.controllers.interfaces;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.data.models.Property;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
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
    void createBuilding(
            @RequestPart("building") @Valid CreateBuildingRequest createBuildingRequest,
            @RequestPart("images") MultipartFile[] imageFiles
    );

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves a list of all buildings with basic information.")
    List<BuildingResponse> getBuildings();

    @GetMapping("/details")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves a paginated list of all buildings with detailed information.")
    Page<BuildingDetailsResponse> getBuildingsDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    );
    @GetMapping("/nearby")
    @ResponseStatus(OK)
    @Operation(summary = "Get buildings near a location within a radius in km")
    List<BuildingResponse> getBuildingsNear(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double radiusKm
    );
    @GetMapping("/nearby/poi")
    @ResponseStatus(OK)
    @Operation(summary = "Get properties near POIs of a given type within viewport and radius in km")
    List<BuildingResponse> getBuildingsNearPoi(
            @RequestParam String poiType,
            @RequestParam Double radiusKm,
            @RequestParam Double north,
            @RequestParam Double south,
            @RequestParam Double east,
            @RequestParam Double west,
            @RequestParam(required = false) Integer limit
    );
    @GetMapping("/{buildingId}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves detailed information about a specific building by its ID.")
    BuildingDetailsResponse getBuildingDetails(@PathVariable UUID buildingId);

    @PatchMapping("/{buildingId}")
    @ResponseStatus(OK)
    @Operation(summary = "Updates the details of an existing building by its ID.")
    BuildingDetailsResponse updateBuilding(
            @PathVariable UUID buildingId,
            @RequestPart("building") @Valid UpdateBuildingRequest updateBuildingRequest,
            @RequestPart(value = "images", required = false) MultipartFile[] imageFiles
    );
    @DeleteMapping("/{buildingId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletes a building by its ID.")
    void deleteBuilding(@PathVariable UUID buildingId);

    @PatchMapping("/{buildingId}/restore")
    @ResponseStatus(CREATED)
    @Operation(summary = "Restores a deleted building by its ID.")
    void restoreBuilding(@PathVariable UUID buildingId);

    @GetMapping("/{buildingId}/properties")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves a list of properties associated with a specific building by its ID.")
    List<PropertyDetailsResponse> getBuildingProperties(@PathVariable UUID buildingId);


}