package com.imaee.propinq.users.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.users.data.pipes.PhoneNumberConverter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Convert;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.imaee.propinq.users.data.enums.Role;


import java.util.Collections;
import java.util.List;
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
