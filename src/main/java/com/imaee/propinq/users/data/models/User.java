package com.imaee.propinq.users.data.models;

import com.imaee.propinq.users.data.enums.Role;
import com.imaee.propinq.users.data.pipes.PhoneNumberConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    private final UUID userId = UUID.randomUUID();

    @NotNull
    private String password;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String email;
    
    @NotNull
    private String address;

    @NotNull
    @Convert(converter = PhoneNumberConverter.class)
    private String phoneNumber;
 
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.TENANT;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    @Builder.Default
    private List<Token> tokens = new ArrayList<>();
  
    @Builder.Default
    private boolean activated = false;
  
    @Builder.Default
    private boolean deleted = false;

    public String getFullName() { return firstName + " " + lastName; }
}