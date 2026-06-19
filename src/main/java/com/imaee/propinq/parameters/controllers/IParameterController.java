package com.imaee.propinq.parameters.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/parameters")
@Tag(
        name = "Parameters",
        description = "Operations for managing parameters."
)
public interface IParameterController {

    @GetMapping("/max-price")
    @ResponseStatus(OK)
    @Operation(summary = "Returns the max price available.")
    Double maxPrice();

    @GetMapping("/min-price")
    @ResponseStatus(OK)
    @Operation(summary = "Returns the max price available.")
    Double minPrice();

    @GetMapping("/rooms")
    @ResponseStatus(OK)
    @Operation(summary = "Returns the rooms options available.")
    List<Integer> rooms();

    @GetMapping("/bathrooms")
    @ResponseStatus(OK)
    @Operation(summary = "Returns the bathrooms options available.")
    List<Integer> bathrooms();
}