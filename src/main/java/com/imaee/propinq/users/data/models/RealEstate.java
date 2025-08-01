package com.imaee.propinq.users.data.models;

import com.imaee.propinq.users.data.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity(name = "real_states")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class RealEstate extends User{

    @NonNull
    private String address;

    @NonNull
    @Column(unique = true)
    private String companyName;

    @NonNull
    private String cuit;

    @NonNull
    private String legalName;
}