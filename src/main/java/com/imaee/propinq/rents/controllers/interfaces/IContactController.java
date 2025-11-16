package com.imaee.propinq.rents.controllers.interfaces;

import com.imaee.propinq.rents.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.rents.controllers.requests.ContactRequest;
import com.imaee.propinq.rents.controllers.responses.ContactResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/v1/contacts")
@Tag(
        name = "Contact Requests",
        description = "Operations for managing contact requests, previous to rents."
)
public interface IContactController {

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Saves a new contact request for a property.")
    void saveContactRequest(@RequestBody @Valid ContactRequest contactRequest);

    @GetMapping("/{contactId}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves an existing contact request by its ID.")
    ContactResponse getContactRequest(@PathVariable UUID contactId);

    @PatchMapping("/{contactId}/answer")
    @ResponseStatus(OK)
    @Operation(summary = "Answers a contact request. The owner can either accept it or reject it.")
    void answerContactRequest(@PathVariable UUID contactId, @RequestBody @Valid AnswerContactRequest answerContactRequest);
}