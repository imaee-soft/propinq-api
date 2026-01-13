package com.imaee.propinq.rents.controllers.interfaces;

import com.imaee.propinq.rents.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.rents.controllers.requests.ContactRequest;
import com.imaee.propinq.rents.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.rents.controllers.responses.ContactResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RequestMapping("/api/v1/contacts")
@Tag(
        name = "Contact Requests",
        description = "Operations for managing contact requests, previous to rents."
)
public interface IContactController {

    @GetMapping("/tenant")
    @ResponseStatus(OK)
    @Operation(summary = "Saves a new contact request for a property.")
    Page<ContactDetailResponse> getTenantContacts(
            @RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
            @RequestParam(defaultValue = "8", name = "size") Integer pageSize
    );

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

    @DeleteMapping("/{contactId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletes a contact request. That can be done only for issuers.")
    void deleteContactRequest(@PathVariable UUID contactId);
}