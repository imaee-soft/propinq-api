package com.imaee.propinq.users.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.users.data.pipes.PhoneNumberConverter;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import com.imaee.propinq.users.data.enums.Role;
import lombok.experimental.SuperBuilder;


import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name="users")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class User {

    @Id
    @GeneratedValue
    private UUID userId;

    @NonNull
    @Column(unique = true)
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

    @OneToMany
    private List<Building> buildings = Collections.emptyList();

    @OneToMany
    private List<Property> properties = Collections.emptyList();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.TENANT;

    private Boolean deleted = false;
}
