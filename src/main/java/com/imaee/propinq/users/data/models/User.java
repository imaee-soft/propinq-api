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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import lombok.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    private final UUID userId = UUID.randomUUID();

    @NonNull
    @Column(unique = true)
    private String dni;

    @NonNull
    private String password;

    @NonNull
    private LocalDate birthDate;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @Column(unique = true)
    private String email;
    
    @NonNull
    private String address;

    @NonNull
    @Convert(converter = PhoneNumberConverter.class)
    private String phoneNumber;
 
    private String cuit;

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

