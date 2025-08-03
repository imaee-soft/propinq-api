package com.imaee.propinq.neighborhood.controllers.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
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

@RequestMapping("/api/v1/localities")
public interface ILocalityController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createLocality(@RequestBody @Valid LocalityRequest localityRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<LocalityResponse> getLocalities();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    LocalityResponse getLocality(@PathVariable UUID id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void updateLocality(@PathVariable UUID id,
                        @RequestBody @Valid LocalityRequest localityRequest);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLocality(@PathVariable UUID id);
}
