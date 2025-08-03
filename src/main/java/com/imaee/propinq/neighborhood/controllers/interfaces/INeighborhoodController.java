package com.imaee.propinq.neighborhood.controllers.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.NeighborhoodRequest;
import com.imaee.propinq.neighborhood.controllers.responses.NeighborhoodResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/neighborhoods")
public interface INeighborhoodController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createNeighborhood(@RequestBody @Valid NeighborhoodRequest neighborhoodRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<NeighborhoodResponse> getNeighborhoods();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    NeighborhoodResponse getNeighborhood(@PathVariable UUID id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void updateNeighborhood(@PathVariable UUID id,
                            @RequestBody @Valid NeighborhoodRequest neighborhoodRequest);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteNeighborhood(@PathVariable UUID id);
}
