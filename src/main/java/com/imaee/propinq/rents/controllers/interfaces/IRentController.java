package com.imaee.propinq.rents.controllers.interfaces;

import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/rents")
@Tag(
        name = "Rents",
        description = "Operations for managing rents."
)
public interface IRentController {

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Saves a new rent from a previous contact.")
    void saveRent(
            @RequestPart("rent") @Valid RentRequest rentRequest,
            @RequestPart("contract") MultipartFile contract
    );

    @GetMapping("/owner")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves all owner's rents.")
    Page<SimpleRent> getOwnerRents(
            @RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
            @RequestParam(defaultValue = "6", name = "size") Integer pageSize
    );
}