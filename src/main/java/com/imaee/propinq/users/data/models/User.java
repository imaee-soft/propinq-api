package com.imaee.propinq.users.data.models;

import jakarta.persistence.*;
import lombok.*;
import com.imaee.propinq.users.data.enums.Role;


import java.util.UUID;

@Entity(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    private UUID userId = UUID.randomUUID();

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @NonNull
    //@Convert(converter = PhoneNumberConverter.class)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.TENANT;





}
