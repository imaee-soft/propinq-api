package com.imaee.propinq.users.controllers.interfaces;

import com.imaee.propinq.config.utils.Endpoints;

import com.imaee.propinq.users.controllers.responses.ProfileChangeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(
        name = "User profiles",
        description = "Operations for managing user profiles."
)
@RequestMapping(Endpoints.API_V1 + "/user-profiles")
public interface IUserProfilesController {

    @PostMapping("/owner-request")
    @ResponseStatus(CREATED)
    @Operation(summary = "Saves a new owner-transition request.")
    void saveOwnerProfileRequest();

    @GetMapping("/requests")
    @ResponseStatus(OK)
    @Operation(summary = "Retrieves all user profile change requests.")
    Page<ProfileChangeResponse> getProfileChanges(
            @RequestParam(defaultValue = "0", name = "page") Integer pageNumber,
            @RequestParam(defaultValue = "6", name = "size") Integer pageSize
    );

    @PatchMapping("/requests/{profileChangeId}/accept")
    @ResponseStatus(OK)
    @Operation(summary = "Accepts a profile change requets.")
    void acceptProfileChange(@PathVariable UUID profileChangeId);

    @PatchMapping("/requests/{profileChangeId}/reject")
    @ResponseStatus(OK)
    @Operation(summary = "Accepts a profile change requets.")
    void rejectProfileChange(@PathVariable UUID profileChangeId);
}