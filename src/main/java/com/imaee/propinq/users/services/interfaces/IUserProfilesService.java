package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.controllers.responses.ProfileChangeResponse;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.domain.Page;

public interface IUserProfilesService {
    void saveOwnerProfileRequest();
    ProfileChangeResponse getProfileChangeByUser(User user);
    Page<ProfileChangeResponse> getProfileChanges(Integer pageNumber, Integer pageSize);
}