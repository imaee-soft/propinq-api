package com.imaee.propinq.users.mappers;

import com.imaee.propinq.users.controllers.responses.ProfileChangeResponse;
import com.imaee.propinq.users.data.models.ProfileChange;
import com.imaee.propinq.users.data.models.User;

public class ProfileChangeMapper {

    public static ProfileChange buildProfileChange(User user) {
        return ProfileChange.builder()
                .user(user)
                .build();
    }

    public static ProfileChangeResponse buildProfileChangeResponse(ProfileChange profileChange) {
        return ProfileChangeResponse.builder()
                .profileChangeId(profileChange.getProfileChangeId())
                .roleRequested(profileChange.getRoleRequested().name())
                .profileChangeState(profileChange.getState().name())
                .createdAt(profileChange.getCreatedAt())
                .userFullName(profileChange.getUser().getFullName())
                .userPhoneNumber(profileChange.getUser().getPhoneNumber())
                .build();
    }
}