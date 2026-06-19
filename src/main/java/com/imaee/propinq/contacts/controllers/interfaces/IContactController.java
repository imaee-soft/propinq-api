package com.imaee.propinq.contacts.controllers.interfaces;

import com.imaee.propinq.config.utils.Endpoints;

import com.imaee.propinq.contacts.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.contacts.controllers.requests.CancelContactRequest;
import com.imaee.propinq.contacts.controllers.requests.ContactRequest;
import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.contacts.controllers.responses.ContactResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RequestMapping(Endpoints.API + "/contacts")
@Tag(
        name = "Contact Requests",
        description = "Operations for managing contact requests, previous to rents."
)
public interface IContactController {

    @GetMapping("/tenant")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves all tenant's contact requests.")
    Page<ContactDetailResponse> getTenantContacts(
            @RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
            @RequestParam(defaultValue = "6", name = "size") Integer pageSize
    );

    @GetMapping("/owner")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves all owner's contact requests.")
    Page<ContactDetailResponse> getOwnerContacts(
            @RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
            @RequestParam(defaultValue = "6", name = "size") Integer pageSize,
            @RequestParam(defaultValue = "", name = "surname") String surname,
            @RequestParam(defaultValue = "all", name = "status") String status
    );

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Saves a new contact request for a property.")
    void saveContactRequest(@RequestBody @Valid ContactRequest contactRequest);

    @GetMapping("/{contactId:[0-9a-fA-F\\\\-]{36}}")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves an existing contact request by its ID.")
    ContactResponse getContactRequest(@PathVariable UUID contactId);

    @GetMapping("/{contactId:[0-9a-fA-F\\\\-]{36}}/details")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves an existing contact details by its ID.")
    ContactDetailResponse getContactDetails(@PathVariable UUID contactId);

    @PatchMapping("/{contactId:[0-9a-fA-F\\\\-]{36}}/answer")
    @ResponseStatus(OK)
    @Operation(summary = "Answers a contact request. The owner can either accept it or reject it.")
    void answerContactRequest(@PathVariable UUID contactId, @RequestBody @Valid AnswerContactRequest answerContactRequest);

    @PatchMapping("/{contactId:[0-9a-fA-F\\\\-]{36}}/cancel")
    @ResponseStatus(OK)
    @Operation(summary = "Cancels a contact request after the negotiation failed. Only the owner can do this.")
    void cancelContact(@PathVariable UUID contactId, @RequestBody @Valid CancelContactRequest cancelContactRequest);

    @DeleteMapping("/{contactId:[0-9a-fA-F\\\\-]{36}}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletes a contact request. That can be done only for issuers.")
    void deleteContactRequest(@PathVariable UUID contactId);
}