package com.imaee.propinq.users.mappers;

import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.users.controllers.responses.UserResponse;
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

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .dni(user.getDni())
                .birthDate(user.getBirthDate())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .cuit(user.getCuit())
                .build();
    }
}