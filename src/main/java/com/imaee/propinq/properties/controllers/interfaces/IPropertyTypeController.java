package com.imaee.propinq.properties.controllers.interfaces;


import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/property-types")
@Tag(
        name = "Property Types",
        description = "Operations for managing and querying property types."
)
public interface IPropertyTypeController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves a list of all property types with basic information.")
    List<PropertyTypeResponse> getPropertyTypes();

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves a list of all property types including deleted ones.")
    List<PropertyTypeResponse> getPropertyTypeAll();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new property type with the provided details.")
    PropertyTypeResponse createPropertyType(@RequestBody @Valid PropertyTypeRequest propertyTypeRequest);

    @PutMapping("/{propertyTypeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates the details of an existing property type identified by its ID.")
    PropertyTypeResponse updatePropertyType(@PathVariable UUID propertyTypeId,
                                            @RequestBody @Valid PropertyTypeRequest propertyTypeRequest);

    @DeleteMapping("/{propertyTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft deletes a property type identified by its ID.")
    void deletePropertyType(@PathVariable UUID propertyTypeId);

    @PostMapping("/{propertyTypeId}/recover")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Recovers a previously soft-deleted property type identified by its ID.")
    void recoverPropertyType(@PathVariable UUID propertyTypeId);




}