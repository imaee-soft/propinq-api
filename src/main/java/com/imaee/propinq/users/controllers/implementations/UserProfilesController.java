package com.imaee.propinq.users.controllers.implementations;

import com.imaee.propinq.users.controllers.interfaces.IUserProfilesController;
import com.imaee.propinq.users.controllers.responses.ProfileChangeResponse;
import com.imaee.propinq.users.services.interfaces.IUserProfilesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserProfilesController implements IUserProfilesController {

    private final IUserProfilesService userProfilesService;

    @Override
    public void saveOwnerProfileRequest() {
        userProfilesService.saveOwnerProfileRequest();
    }

    @Override
    public Page<ProfileChangeResponse> getProfileChanges(Integer pageNumber, Integer pageSize) {
        return userProfilesService.getProfileChanges(pageNumber, pageSize);
    }
}