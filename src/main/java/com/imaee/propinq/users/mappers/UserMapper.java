package com.imaee.propinq.users.mappers;

import com.imaee.propinq.users.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.data.models.User;

import java.time.LocalDate;

public class UserMapper {

    private UserMapper() {}

    public static User toUser(SignUpRequest createUserRequest, String encodedPassword) {
        return User.builder()
                .dni(createUserRequest.dni())
                .birthDate(LocalDate.parse(createUserRequest.birthDate()))
                .password(encodedPassword)
                .firstName(createUserRequest.firstName())
                .lastName(createUserRequest.lastName())
                .email(createUserRequest.email())
                .address(createUserRequest.address())
                .phoneNumber(createUserRequest.phoneNumber())
                .build();
    }
}
