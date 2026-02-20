package com.imaee.propinq.rents.controllers.interfaces;

import com.imaee.propinq.projections.responses.Projection;
import com.imaee.propinq.rents.controllers.requests.CancelRentRequest;
import com.imaee.propinq.rents.controllers.requests.RentDocumentRequest;
import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.RentDetail;
import com.imaee.propinq.rents.controllers.responses.SaveRentResponse;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

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
    SaveRentResponse saveRent(
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

    @GetMapping("/tenant")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves all tenant's rents.")
    Page<SimpleRent> getTenantRents(
            @RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
            @RequestParam(defaultValue = "6", name = "size") Integer pageSize
    );

    @GetMapping("/{rentId:[0-9a-fA-F\\\\-]{36}}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves a whole rent.")
    RentDetail getRent(@PathVariable UUID rentId);

    @GetMapping("/{rentId:[0-9a-fA-F\\\\-]{36}}/projection")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves a rent projection.")
    List<Projection> getRentProjection(@PathVariable UUID rentId);

    @PostMapping("/document")
    @ResponseStatus(OK)
    @Operation(summary = "Saves a new rent document.")
    void saveDocument(
            @RequestPart("document") @Valid RentDocumentRequest rentDocumentRequest,
            @RequestPart("content") MultipartFile document
    );

    @PostMapping("/{rentId:[0-9a-fA-F\\\\-]{36}}/cancel")
    @ResponseStatus(OK)
    @Operation(summary = "Cancels a current rent with a specified reason.")
    void cancelRent(
            @PathVariable UUID rentId,
            @RequestBody @Valid CancelRentRequest cancelRentRequest
    );
}