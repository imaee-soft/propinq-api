package com.imaee.propinq.properties.controllers.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RequestMapping("/api/v1/properties")
@Tag(
        name = "Properties",
        description = "Operations for managing and querying properties."
)
public interface IPropertyController {

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves a list of all properties with basic information.")
    List<PropertyResponse> getProperties();

    @GetMapping("/details")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves a paginated list of all user properties with detailed information.")
    Page<PropertyDetailsResponse> getPropertiesDetails(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "15") int size);

    @GetMapping("/nearby")
    @ResponseStatus(OK)
    @Operation(summary = "Get properties near a location within a radius in km")
    List<PropertyResponse> getPropertiesNear(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double radiusKm
    );
    @GetMapping("/nearby/poi")
    @ResponseStatus(OK)
    @Operation(summary = "Get properties near POIs of a given type within viewport and radius in km")
    List<PropertyResponse> getPropertiesNearPoi(
            @RequestParam String poiType,
            @RequestParam Double radiusKm,
            @RequestParam Double north,
            @RequestParam Double south,
            @RequestParam Double east,
            @RequestParam Double west,
            @RequestParam(required = false) Integer limit
    );

    @GetMapping("/{propertyId}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves detailed information about a specific building by its ID.")
    PropertyDetailsResponse getPropertyDetails(@PathVariable UUID propertyId);

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Saves a new property.")
    void createProperty(
            @RequestPart("property") @Valid CreatePropertyRequest createPropertyRequest,
            @RequestPart("images") MultipartFile[] imageFiles
    );

    @DeleteMapping("/{propertyId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletes a property by its ID.")
    void deleteProperty(@PathVariable UUID propertyId);

    @PatchMapping("/{propertyId}/restore")
    @ResponseStatus(CREATED)
    @Operation(summary = "Restores a deleted property by its ID.")
    void restoreProperty(@PathVariable UUID propertyId);
}