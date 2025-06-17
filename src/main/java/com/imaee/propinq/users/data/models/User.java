package com.imaee.propinq.users.data.models;

import com.imaee.propinq.shared.data.models.Locatable;
import com.imaee.propinq.users.data.pipes.PhoneNumberConverter;
import jakarta.persistence.*;
import lombok.*;
import com.imaee.propinq.users.data.enums.Role;


import java.util.UUID;

@Entity(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User extends Locatable {

    @Id
    private final UUID userId = UUID.randomUUID();

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
    @Convert(converter = PhoneNumberConverter.class)
    private String phoneNumber;

    @NonNull
    private String address;

    private String cuit;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.TENANT;





}
