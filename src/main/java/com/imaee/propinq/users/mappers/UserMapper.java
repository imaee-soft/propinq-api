package com.imaee.propinq.users.mappers;

import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.data.models.User;

public class UserMapper {
    private UserMapper() {
    }

    public static User toUser(SignUpRequest createUserRequest){
        return User.builder()
                .username(createUserRequest.username())
                .password(createUserRequest.password())
                .firstName(createUserRequest.firstName())
                .lastName(createUserRequest.lastName())
                .email(createUserRequest.email())
                .address(createUserRequest.address())
                .phoneNumber(createUserRequest.phoneNumber())
                .build();
    }
}
