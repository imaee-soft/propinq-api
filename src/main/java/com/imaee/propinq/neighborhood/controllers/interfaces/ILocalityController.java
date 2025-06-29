package com.imaee.propinq.neighborhood.controllers.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/localities")
public interface ILocalityController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> createLocality(@RequestBody @Valid LocalityRequest localityRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<LocalityResponse>> getLocalities();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<LocalityResponse> getLocality(@PathVariable UUID id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> updateLocality(@PathVariable UUID id,
                                        @RequestBody @Valid LocalityRequest localityRequest);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteLocality(@PathVariable UUID id);
}
