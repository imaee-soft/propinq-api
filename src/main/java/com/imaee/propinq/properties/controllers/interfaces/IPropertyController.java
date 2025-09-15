package com.imaee.propinq.properties.controllers.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.imaee.propinq.buildings.data.enums.BuildingType;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

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

    @GetMapping("/{propertyId}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves detailed information about a specific building by its ID.")
    PropertyDetailsResponse getPropertyDetails(@PathVariable UUID propertyId);

        @GetMapping("/filter")
        @ResponseStatus(OK)
        @Operation(summary = "Retrieves a list of properties filtered by various criteria.")
        List<PropertyDetailsResponse> filterProperties(@ModelAttribute PropertyFilterRequest filter);
        }
