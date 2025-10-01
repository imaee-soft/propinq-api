package com.imaee.propinq.neighborhoods.controllers.interfaces;

import com.imaee.propinq.neighborhoods.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/neighborhoods")
@Tag(
        name = "Neighborhoods",
        description = "Operations for managing and querying neighborhoods."
)
public interface INeighborhoodController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new neighborhood with the provided details.")
    void createNeighborhood(@RequestBody @Valid NeighborhoodRequest neighborhoodRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves a list of all neighborhoods with basic information.")
    List<NeighborhoodResponse> getNeighborhoods();

    @GetMapping("/{neighborhoodId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves detailed information about a specific neighborhood by its ID.")
    NeighborhoodResponse getNeighborhood(@PathVariable UUID neighborhoodId);

    @PutMapping("/{neighborhoodId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates the details of an existing neighborhood identified by its ID.")
    void updateNeighborhood(@RequestBody @Valid NeighborhoodRequest neighborhoodRequest,
                            @PathVariable UUID neighborhoodId);

    @DeleteMapping("/{neighborhoodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft deletes a neighborhood identified by its ID.")
    void deleteNeighborhood(@PathVariable UUID neighborhoodId);

    @PostMapping("/{neighborhoodId}/recover")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Recovers a soft-deleted neighborhood identified by its ID.")
    void recoverNeighborhood(@PathVariable UUID neighborhoodId);
}