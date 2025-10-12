package com.imaee.propinq.properties.controllers.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/properties")
@Tag(
        name = "Properties",
        description = "Operations for managing and querying properties."
)
@CrossOrigin(origins = "http://localhost:4200")public interface IPropertyController {
    
    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves all properties without any filters.")
    List<PropertyResponse> getProperties();

    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves properties filtered by the provided criteria.")
    List<PropertyResponse> getProperties(@ModelAttribute PropertyFilterRequest filter);

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
    }
