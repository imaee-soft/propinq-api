package com.imaee.propinq.neighborhood.controllers.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhood.controllers.responses.NeighborhoodResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/neighborhood")
public interface INeighborhoodController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> createNeighborhood(@RequestBody @Valid NeighborhoodRequest neighborhoodRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<NeighborhoodResponse>> getNeighborhoods();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<NeighborhoodResponse> getNeighborhood(@PathVariable UUID id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> updateNeighborhood(@PathVariable UUID id,
                                        @RequestBody @Valid NeighborhoodRequest neighborhoodRequest);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> deleteNeighborhood(@PathVariable UUID id);
}
