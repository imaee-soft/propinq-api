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
@CrossOrigin(origins = "http://localhost:4200")
public interface IPropertyController {
    
    @GetMapping
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves properties with optional filters. Supports attribute filters (buildingType, price, etc.), location filters (latitude, longitude, radius), and POI filters (poiType, viewport). If no filters are provided, returns all properties.")
    List<PropertyResponse> getProperties(@ModelAttribute PropertyFilterRequest filter);

    @GetMapping("/{propertyId}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves detailed information about a specific property by its ID.")
    PropertyDetailsResponse getPropertyDetails(@PathVariable UUID propertyId);
}
