package com.imaee.propinq.properties.controllers.interfaces;


import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/propertyTypes")
public interface IPropertyTypeController {

    @GetMapping("/getPropertyTypes")
    List<PropertyTypeResponse> getPropertyTypes();

    @PostMapping("/createPropertyType")
    PropertyTypeResponse createPropertyType(@RequestBody @Valid PropertyTypeRequest propertyTypeRequest);

    @PutMapping("/updatePropertyType")
    PropertyTypeResponse updatePropertyType(@RequestBody @Valid PropertyTypeRequest propertyTypeRequest, @PathVariable UUID id);

    @DeleteMapping("/deletePropertyType/{id}")
    ResponseEntity<Void> deletePropertyType(@PathVariable UUID id);

    @PutMapping("/recoverPropertyType/{id}")
    ResponseEntity<Void> recoverPropertyType(@PathVariable UUID id);




}
