package com.imaee.propinq.properties.controllers.interfaces;


import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/propertyTypes")
public interface IPropertyTypeController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<PropertyTypeResponse> getPropertyTypes();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PropertyTypeResponse createPropertyType(@RequestBody @Valid PropertyTypeRequest propertyTypeRequest);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PropertyTypeResponse updatePropertyType(@RequestBody @Valid PropertyTypeRequest propertyTypeRequest, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePropertyType(@PathVariable UUID id);

    @PostMapping("/{id}/recover")
    @ResponseStatus(HttpStatus.CREATED)
    void recoverPropertyType(@PathVariable UUID id);




}
