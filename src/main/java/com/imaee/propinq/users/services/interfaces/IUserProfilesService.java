package com.imaee.propinq.users.services.interfaces;

import com.imaee.propinq.users.controllers.responses.ProfileChangeResponse;
import com.imaee.propinq.users.data.models.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IUserProfilesService {
    void saveOwnerProfileRequest();
    ProfileChangeResponse getProfileChangeByUser(User user);
    Page<ProfileChangeResponse> getProfileChanges(Integer pageNumber, Integer pageSize);
    void acceptProfileChange(UUID profileChangeId);
    void rejectProfileChange(UUID profileChangeId);
}