package com.imaee.propinq.neighborhood.controllers.interfaces;

import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/locality")
public interface ILocalityController {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    LocalityResponse create(@RequestBody @Valid LocalityRequest localityRequest);

}
