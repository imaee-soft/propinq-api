package com.imaee.propinq.localities.controllers.interfaces;

import com.imaee.propinq.config.utils.Endpoints;

import com.imaee.propinq.localities.controllers.requests.LocalityRequest;
import com.imaee.propinq.localities.controllers.responses.LocalityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(Endpoints.API + "/localities")
@Tag(
        name = "Localities",
        description = "Operations for managing and querying localities."
)
public interface ILocalityController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new locality with the provided details.")
    void createLocality(@RequestBody @Valid LocalityRequest localityRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves a list of all localities with basic information.")
    List<LocalityResponse> getLocalities();

    @GetMapping("/{localityId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves detailed information about a specific locality by its ID.")
    LocalityResponse getLocality(@PathVariable UUID localityId);

    @PutMapping("/{localityId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates the details of an existing locality identified by its ID.")
    void updateLocality(@RequestBody @Valid LocalityRequest localityRequest,
                        @PathVariable UUID localityId);

    @DeleteMapping("/{localityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft deletes a specific locality by its ID.")
    void deleteLocality(@PathVariable UUID localityId);

    @PostMapping("/{localityId}/recover")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Recovers a soft-deleted locality identified by its ID.")
    void recoverLocality(@PathVariable UUID localityId);

    @GetMapping("/by-province/{provinceId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves a list of localities associated with a specific province ID.")
    List<LocalityResponse> getLocalitiesByProvince(@PathVariable UUID provinceId);
}
